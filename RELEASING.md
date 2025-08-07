Releasing
=========

Prerequisites
-------------

1. Get permissions to publish on Maven Central (sonatype)
    * Create a user [here][create_user]
    * Ask for permissions on the existing ticket [here][sonatype_ticket]

2. Make sure you have a GPG client installed and configured. Check [GNUPG][gnupg]
    * Refer to the [GPG Keys][example] guide if you need to set up GPG keys for signing. Check also the GPG Guide [here][gnupg_guide]
    * Install the GPG client. In Linux:  `sudo apt install gnupg`
    * Generate a Key Pair with:  `gpg --gen-key`
    * Distribute the public Key with: `gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXXXXXXXXXXXXXXXXXXXXX`

3. In `~/.m2/settings.xml`, set the following:

```xml
<settings>
  <servers>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>your-nexus-tokenuser</username>
      <password>your-nexus-tokenkey</password>
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
        <gpg.passphrase>the-passphrase</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

Release to Maven Central
-------------

1. Do the following tasks sequentially:
    * Update the RELEASE_NOTES.md for the impending release
    * Make sure the javadocs generation does not have any errors:  `mvn javadoc:javadoc`
    * Make sure the MO Testbeds are passing
    * Update the versions of ALL the pom files. Example, from: 12.2-SNAPSHOT to: 12.2
    * Delete the local ~/.m2/repository and make sure that the project can be built from scratch

2. Compile the projects with sources and javadocs (and enter your GPG Passphrase when prompted) and deploy to OSSRH (Maven Central):
```
mvn clean deploy -P release
```
* Note 1: On some Windows machines sometimes the command does not go through. Workaround: Use an Ubuntu VM
* Note 2: The command might fail in the end because the Nexus Repository is slow with the checks. Wait and check the progress on the Nexus Repository Manager website mentioned below. Eventually, the status will change from open to closed. After this occurs, the software can then be released, check the next step.

3. Inspect the staging repository in the Maven Central Portal [here][oss]
    * Release it if everything looks alright 
    * Or Drop it if there are errors

4. Tag the release (tag name example: release-12.2)
    * Tag: `git tag release-XX.Y`
    * Then push it to the repo: `git push origin --tags`

5. Create a Pull Request from the new version branch to master. Please "Squash and Merge" it.

6. Create a GitHub release in: https://github.com/esa/mo-services-java/releases

7. Create a new branch from master:
```
git checkout master
git pull
git checkout -b vAA.B
```

Extra
-------------
Complete Maven Central publishing guide available [here][sonatype_guide]!

 [create_user]: https://issues.sonatype.org/secure/Signup!default.jspa
 [sonatype_ticket]: https://issues.sonatype.org/browse/OSSRH-38566
 [sonatype_guide]: https://central.sonatype.org/publish/publish-guide/
 [gnupg]: https://www.gnupg.org/
 [example]: https://square.github.io/okio/releasing/#prerequisite-gpg-keys
 [gnupg_guide]:  https://central.sonatype.org/publish/requirements/gpg/
 [oss]: https://central.sonatype.com/publishing
