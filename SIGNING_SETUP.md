# Setup Instructions for Nana Comik Signing

## Step 1: Generate the Keystore (One-time setup)

Run this command in your local machine (where you have `keytool`):

```bash
keytool -genkey -v -keystore nanacomik.keystore \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias nanacomik \
  -storepass nanacomik123 \
  -keypass nanacomik123 \
  -dname "CN=Nana Comik,O=NanaComik,C=US"
```

This creates: `nanacomik.keystore`

## Step 2: Encode Keystore for GitHub Secrets

Run this command in the same directory as the keystore:

```bash
cat nanacomik.keystore | base64
```

Copy the entire base64 output.

## Step 3: Add GitHub Secrets

Go to your repository settings (GitHub → Settings → Secrets and variables → Actions) and add:

| Secret Name | Value |
| --- | --- |
| `KEYSTORE_FILE` | Paste the base64 encoded keystore from Step 2 |
| `KEYSTORE_PASSWORD` | `nanacomik123` |
| `KEY_ALIAS` | `nanacomik` |
| `KEY_PASSWORD` | `nanacomik123` |

## Step 4: Upload Keystore to Repository

Copy the `nanacomik.keystore` file to:
```
/app/nanacomik.keystore
```

The file should be in the `app/` folder (same level as `build.gradle.kts`).

**Important**: Add to `.gitignore` if not already:
```
app/*.keystore
```

## Step 5: Commit and Push

After adding the keystore file:

```bash
git add app/nanacomik.keystore
git add app/build.gradle.kts
git add i18n/src/commonMain/moko-resources/base/strings.xml
git add .github/workflows/build_push.yml
git add .github/workflows/build_release.yml
git commit -m "Change app to Nana Comik with new package name and signing key"
git push origin master
```

## Verification

- Package name changed to: `app.nanacomik` (beta: `app.nanacomik.beta`)
- App display name: `Nana Comik`
- APK naming: `Nana-Comik-master-r{commit}.apk` (for push builds)
- APKs will be **signed** with the nanacomik keystore
- Both Komikku and Nana Comik can be installed simultaneously (different package names)
