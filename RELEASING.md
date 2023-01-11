Releasing
=========

1. Update the RELEASE_NOTES.md for the impending release.
2. Update the versions of the pom files.
3. `git commit -am "Update changelog for X.Y.Z."` (where X.Y.Z is the new version).
4. Run the command:  (Prepare it with: `mvn release:prepare -DdryRun`)
```
'mvn clean source:jar javadoc:jar verify && mvn clean release:clean && mvn release:prepare release:perform'
```
4.1 For each project:
    * `What is the release version for "<<Project_Name>>"? (int.esa.ccsds.mo:ARTIFACT_ID) X.Y:` - hit Enter.
4.2 For each project:
    * `What is SCM release tag or label for "<<Project_Name>>"? (int.esa.ccsds.mo:ARTIFACT_ID) X.Y:` - hit Enter.
    * `What is the new development version for "<<Project_Name>>"? (int.esa.ccsds.mo:ARTIFACT_ID) X.(Y + 1)-SNAPSHOT:` - enter `X.(Y + 1).0-SNAPSHOT`.
4.3  Enter your GPG Passphrase when prompted.
5. Visit Sonatype Nexus and promote the artifact.

If step 4 or 5 fails:

  * Drop the Sonatype repo, 
  * Fix the problem,
  * Manully revert the version change in `pom.xml` made by `mvn-release`,
  * Commit,
  * And start again at step 4.

Prerequisites
-------------

In `~/.m2/settings.xml`, set the following:

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

In your shell's `.rc` file, set the following:


Refer to the [GPG Keys][example] guide if you need to set up GPG keys for signing.

 [example]: https://square.github.io/okio/releasing/#prerequisite-gpg-keys
