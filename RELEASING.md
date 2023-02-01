Releasing
=========

Prerequisites
-------------

1. Get permissions to publish on Maven Central (sonatype)
    * Create a user [here][create_user]
    * Ask for permissions on the existing ticket [here][sonatype_ticket]

2. Make sure you have a GPG client installed and configured. Check [GNUPG][gnupg]
    * Refer to the [GPG Keys][example] guide if you need to set up GPG keys for signing.
    * All the information GPG is available [here][gnupg_guide]
    * Install the GPG client. In Linux:  `sudo apt install gpgv2`
    * Generate a Key Pair with:  `gpg --gen-key`
    * Distribute the public Key with: `gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXXXXXXXXXXXXXXXXXXXXX`

3. In `~/.m2/settings.xml`, set the following:

```xml
<settings>
  <servers>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>your-nexus-username</username>
      <password>your-nexus-password</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.keyname>Firstname.Lastname</gpg.keyname>
        <gpg.passphrase>the_passphrase</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

Release to Maven Central
-------------

1. Update the RELEASE_NOTES.md for the impending release and make sure the javadocs generation does not have errors.

2. Update the versions of the pom files. Example, from: 9.0-SNAPSHOT to: 9.0
`git commit -am "Updates the project versions to X.Y"`

3. Compile the projects with sources and javadocs (and enter your GPG Passphrase when prompted):
```
mvn clean deploy -P release
```

4. Deploy to OSSRH (Maven Central):
```
mvn deploy -P ossrh
```

5. Inspect the staging repository in the Nexus Repository Manager and either trigger a release or drop it:
```
mvn nexus-staging:release
```
If you found a problem, drop it with the command:
```
mvn nexus-staging:drop
```

6. Visit Sonatype Nexus and promote the artifact:  https://oss.sonatype.org/

7. Tag the release in GitHub


Extra
-------------
Complete Maven Central publishing guide available [here][sonatype_guide]!

 [create_user]: https://issues.sonatype.org/secure/Signup!default.jspa
 [sonatype_ticket]: https://issues.sonatype.org/browse/OSSRH-38566
 [sonatype_guide]: https://central.sonatype.org/publish/publish-guide/
 [gnupg]: https://www.gnupg.org/
 [example]: https://square.github.io/okio/releasing/#prerequisite-gpg-keys
 [gnupg_guide]:  https://central.sonatype.org/publish/requirements/gpg/
