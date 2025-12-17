# 📋 Nana Comik Setup Checklist

## Phase 1: Local Setup ⚙️

### 1.1 Generate Keystore
- [ ] Open terminal in `/workspaces/komikku` directory
- [ ] Run keystore generation command:
```bash
keytool -genkey -v -keystore app/nanacomik.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias nanacomik \
  -storepass nanacomik123 -keypass nanacomik123 \
  -dname "CN=Nana Comik,O=NanaComik,C=US"
```
- [ ] Verify `app/nanacomik.keystore` file created (should be ~2KB)

### 1.2 Encode Keystore to Base64
- [ ] Run: `cat app/nanacomik.keystore | base64 > keystore.b64`
- [ ] View: `cat keystore.b64`
- [ ] Copy entire output to clipboard

### 1.3 Verify Generated Files
- [ ] `app/nanacomik.keystore` exists
- [ ] `keystore.b64` contains long base64 string
- [ ] `.gitignore` has `*.keystore` entry (already added)

---

## Phase 2: GitHub Secrets Setup 🔐

### 2.1 Navigate to GitHub Secrets
- [ ] Go to repository settings: `https://github.com/komikku-app/komikku/settings/secrets/actions`
- [ ] Or: Repo → Settings → Secrets and variables → Actions

### 2.2 Create Secret: KEYSTORE_FILE
- [ ] Click "New repository secret"
- [ ] **Name**: `KEYSTORE_FILE`
- [ ] **Value**: Paste the base64 keystore (from keystore.b64)
- [ ] Click "Add secret"

### 2.3 Create Secret: KEYSTORE_PASSWORD
- [ ] Click "New repository secret"
- [ ] **Name**: `KEYSTORE_PASSWORD`
- [ ] **Value**: `nanacomik123`
- [ ] Click "Add secret"

### 2.4 Create Secret: KEY_ALIAS
- [ ] Click "New repository secret"
- [ ] **Name**: `KEY_ALIAS`
- [ ] **Value**: `nanacomik`
- [ ] Click "Add secret"

### 2.5 Create Secret: KEY_PASSWORD
- [ ] Click "New repository secret"
- [ ] **Name**: `KEY_PASSWORD`
- [ ] **Value**: `nanacomik123`
- [ ] Click "Add secret"

### 2.6 Verify All Secrets Created
- [ ] All 4 secrets visible in secrets page
- [ ] No typos in secret names

---

## Phase 3: Commit & Push Changes 🚀

### 3.1 Stage Changes
```bash
cd /workspaces/komikku
git add app/build.gradle.kts
git add i18n/src/commonMain/moko-resources/base/strings.xml
git add .github/workflows/build_push.yml
git add .github/workflows/build_release.yml
git add .gitignore
git add SIGNING_SETUP.md
git add NANA_COMIK_SETUP.md
git add COMPLETE_SETUP.md
git add README_NANA_COMIK.md
git add CHANGES_SUMMARY.md
git add generate-keystore.sh
git add app/nanacomik.keystore
```
- [ ] Commands executed without errors

### 3.2 Verify Staged Changes
```bash
git status
```
- [ ] See all changes staged (green)
- [ ] No unexpected files

### 3.3 Commit
```bash
git commit -m "Add Nana Comik branding with new package name (app.nanacomik) and signing configuration"
```
- [ ] Commit created successfully

### 3.4 Push to Master
```bash
git push origin master
```
- [ ] Push succeeds (no errors)
- [ ] Changes appear on GitHub after 5-10 seconds

---

## Phase 4: CI/CD Verification ✅

### 4.1 Monitor GitHub Actions
- [ ] Go to: `https://github.com/komikku-app/komikku/actions`
- [ ] Wait for "CI" workflow to start
- [ ] Watch progress (usually takes 5-10 minutes)

### 4.2 Check Build Success
- [ ] ✅ All workflow steps complete green
- [ ] ❌ No red error icons
- [ ] Workflow shows "Build app" completed

### 4.3 Verify Signed APK Generated
- [ ] Scroll to "Upload APK" step
- [ ] Should show: `Nana-Comik-master-r{NUMBER}.apk`

### 4.4 Download APK Artifact
- [ ] Click on workflow run
- [ ] Scroll to "Artifacts" section
- [ ] Click download button next to APK
- [ ] Save to your downloads folder

### 4.5 Verify APK Properties
```bash
# Check file size (should be 80-150 MB typically)
ls -lh ~/Downloads/Nana-Comik-master-r*.apk

# Check it's signed (look for "signature scheme")
# (optional - requires apksigner tool)
```
- [ ] APK file exists and is reasonably sized (>50MB)

---

## Phase 5: Device Installation 📱

### 5.1 Enable Developer Mode (if needed)
- [ ] Settings → About phone → Tap "Build Number" 7 times
- [ ] Settings → Developer options → USB Debugging ON

### 5.2 Connect Device
- [ ] Connect Android device via USB
- [ ] Check connection: `adb devices`
- [ ] Should show device with "device" status

### 5.3 Check for Existing Apps
```bash
adb shell pm list packages | grep -i komikku
```
- [ ] Shows: `app.komikku` (Komikku)
- [ ] Should NOT show: `app.nanacomik` yet

### 5.4 Install Nana Comik
```bash
adb install ~/Downloads/Nana-Comik-master-r*.apk
```
- [ ] Installation succeeds (shows "Success")
- [ ] No errors

### 5.5 Verify Installation
```bash
adb shell pm list packages | grep -i comik
```
- [ ] Shows: `app.komikku` (Original Komikku)
- [ ] Shows: `app.nanacomik.beta` (New Nana Comik)

### 5.6 Test on Device
- [ ] Open app launcher
- [ ] ✅ See "Nana Comik" app icon
- [ ] ✅ App launches without errors
- [ ] ✅ Both "Komikku" and "Nana Comik" visible in launcher
- [ ] ✅ App name in header shows "Nana Comik"
- [ ] ✅ Settings/About shows Nana Comik

---

## Phase 6: PDF Testing (Optional) 📄

### 6.1 Create Test Directory
```bash
adb shell mkdir -p /sdcard/Download/manga/pdf_test
```

### 6.2 Copy Test PDF
```bash
# Create a simple PDF file or use an existing one
adb push ~/my_test.pdf /sdcard/Download/manga/pdf_test/
```

### 6.3 Test in App
- [ ] Open Nana Comik
- [ ] Navigate to Local sources
- [ ] Look for pdf_test folder
- [ ] Tap to view PDF
- [ ] ✅ PDF pages render as images
- [ ] ✅ Can scroll through pages

---

## Phase 7: Cleanup 🧹

### 7.1 Remove Local Keystore (Optional but Recommended)
```bash
# Keep a backup first!
# Then remove from repo working directory if not needed
rm keystore.b64  # Remove the unencoded temp file
```
- [ ] Backup of keystore saved securely
- [ ] Temporary files deleted

### 7.2 Verify .gitignore Working
```bash
git status
```
- [ ] NO `app/nanacomik.keystore` shown (should be ignored)

### 7.3 Documentation Notes
- [ ] Read README_NANA_COMIK.md for reference
- [ ] Save COMPLETE_SETUP.md for future reinstalls
- [ ] Share SIGNING_SETUP.md if collaborating

---

## ✨ Final Verification Checklist

### Code Changes ✅
- [x] Package name changed to app.nanacomik
- [x] App display name changed to Nana Comik
- [x] Signing configuration added to build.gradle
- [x] Workflows updated for signed APKs
- [x] Documentation created

### Secrets ✅
- [x] KEYSTORE_FILE added
- [x] KEYSTORE_PASSWORD added
- [x] KEY_ALIAS added
- [x] KEY_PASSWORD added

### Build & Install ✅
- [ ] GitHub Actions build succeeded
- [ ] APK downloaded
- [ ] APK installed on device
- [ ] Nana Comik runs successfully
- [ ] Both apps installed simultaneously

### Testing ✅
- [ ] App name displays "Nana Comik"
- [ ] Package names are different (adb shell pm list packages)
- [ ] PDF reading works (if added test PDF)
- [ ] No crashes on startup

---

## 🎉 Success Criteria

You're done when:

1. ✅ Both "Komikku" and "Nana Comik" appear in your app launcher
2. ✅ Opening "Nana Comik" shows the app with correct branding
3. ✅ GitHub Actions builds successfully on next push
4. ✅ Downloaded APK is signed (not unsigned)
5. ✅ PDF local sources work (if tested)

---

## 📞 Troubleshooting Quick Links

| Issue | Solution |
|-------|----------|
| "Keystore not found" in Actions | Check KEYSTORE_FILE secret has full base64 |
| APK installation fails | Uninstall old version first, ensure different package names |
| App shows wrong name | Clear app cache: `adb shell pm clear app.nanacomik.beta` |
| Can't see both apps | Verify `adb shell pm list packages \| grep comik` shows both |
| PDF doesn't work | Check permissions in app settings, verify PDF file exists |

---

## 📚 Reference Documentation

- **COMPLETE_SETUP.md** - Commands with explanations
- **README_NANA_COMIK.md** - Complete overview
- **SIGNING_SETUP.md** - Detailed setup guide
- **CHANGES_SUMMARY.md** - What was modified
- **NANA_COMIK_SETUP.md** - Technical details

---

**Good luck! 🚀 Start with Phase 1 when ready.**
