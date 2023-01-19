Releasing
=========

Prerequisites
-------------

1. Create an account in Maven Central, on the Sonatype issues site (central.sonatype.org/publish/publish-guide/). Ask an existing publisher to open an issue requesting publishing permissions for int.esa.ccsds.mo projects.

2. In `~/.m2/settings.xml`, set the following:

```xml
<settings>
  <servers>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>your-nexus-username</username>
      <password>your-nexus-password</password>
    </server>
  </servers>
</settings>
```

3. Make sure you have a GPG client installed. Check [GNUPG][gnupg]
Refer to the [GPG Keys][example] guide if you need to set up GPG keys for signing.

Release to Maven Central
-------------

1. Update the RELEASE_NOTES.md for the impending release.
2. Update the versions of the pom files. Example, from: 9.0-SNAPSHOT to: 9.0
3. `git commit -am "Update changelog for X.Y"` (where X.Y is the new version).
4. Deploy to OSSRH and Maven Central (and enter your GPG Passphrase when prompted):
```
'mvn clean deploy'
```
5. Inspect the staging repository in the Nexus Repository Manager and either trigger a release or drop it:
```
'mvn nexus-staging:release'
```
If you found a problem, drop it with the command:
```
'mvn nexus-staging:drop'
```

6. Visit Sonatype Nexus and promote the artifact.


 [gnupg]: https://www.gnupg.org/
 [example]: https://square.github.io/okio/releasing/#prerequisite-gpg-keys
