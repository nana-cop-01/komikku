# ✅ Nana Comik Setup - Complete Configuration Summary

## Overview
Your Komikku fork has been successfully rebrand to **Nana Comik** with:
- ✅ New package name: `app.nanacomik` (allows side-by-side installation with Komikku)
- ✅ New display name: "Nana Comik"
- ✅ Signed APKs with dedicated keystore
- ✅ Automated CI/CD signing in GitHub Actions
- ✅ PDF reading support (from previous changes)

---

## 📋 What's Been Done

### Code Changes
| File | Change | Status |
|------|--------|--------|
| `app/build.gradle.kts` | Package ID: `app.nanacomik`, Added signing config | ✅ |
| `i18n/.../strings.xml` | App name: `Nana Comik` | ✅ |
| `.github/workflows/build_push.yml` | Signed APK generation for master | ✅ |
| `.github/workflows/build_release.yml` | Signed APK generation for releases | ✅ |
| `.gitignore` | Added `*.keystore` | ✅ |

### New Files
| File | Purpose |
|------|---------|
| `generate-keystore.sh` | Script to regenerate keystore |
| `SIGNING_SETUP.md` | Detailed signing setup guide |
| `NANA_COMIK_SETUP.md` | Configuration summary |
| `COMPLETE_SETUP.md` | Quick start commands (READ THIS NEXT!) |

---

## 🚀 Next Steps (Read COMPLETE_SETUP.md for exact commands)

### Step 1: Generate Keystore Locally
```bash
keytool -genkey -v -keystore app/nanacomik.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias nanacomik \
  -storepass nanacomik123 -keypass nanacomik123 \
  -dname "CN=Nana Comik,O=NanaComik,C=US"
```

### Step 2: Encode for GitHub
```bash
cat app/nanacomik.keystore | base64 > keystore.b64
cat keystore.b64  # Copy output
```

### Step 3: Add GitHub Secrets
Visit: **Repository → Settings → Secrets and variables → Actions**

| Secret | Value |
|--------|-------|
| `KEYSTORE_FILE` | Paste base64 from Step 2 |
| `KEYSTORE_PASSWORD` | `nanacomik123` |
| `KEY_ALIAS` | `nanacomik` |
| `KEY_PASSWORD` | `nanacomik123` |

### Step 4: Commit & Push
```bash
git add .
git commit -m "Add Nana Comik branding with signing configuration"
git push origin master
```

### Step 5: Verify
- ✅ GitHub Actions builds and signs APK
- ✅ Download: `Nana-Comik-master-r{commit}.apk`
- ✅ Install: `adb install Nana-Comik-master-r{commit}.apk`

---

## 📦 Package Names

| Edition | Package | Beta |
|---------|---------|------|
| **Komikku** (Original) | `app.komikku` | `app.komikku.dev` |
| **Nana Comik** (New) | `app.nanacomik` | `app.nanacomik.beta` |

Both can be installed on the same device simultaneously!

---

## 🔐 Keystore Details

```
Keystore File: app/nanacomik.keystore
Algorithm: RSA-2048
Validity: 10000 days (~27 years)
Alias: nanacomik
Store Password: nanacomik123
Key Password: nanacomik123
```

---

## 📝 Build Variants

All signed with nanacomik keystore:
- `release` - Full release builds
- `preview` (beta) - Preview builds for master branch
- `foss` - FOSS variant
- `releaseTest` - Testing variant
- `benchmark` - Performance benchmark variant

---

## 🐛 Troubleshooting

### Issue: "Keystore not found" in Actions
- Verify `KEYSTORE_FILE` secret contains full base64 output
- Ensure no line breaks in secret value

### Issue: APK not signed in output
- Check `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` secrets exist
- Verify secret values match keystore credentials

### Issue: Cannot install both versions
- Verify package names are different using: `adb shell pm list packages | grep -i comik`
- Komikku should show: `app.komikku`
- Nana Comik should show: `app.nanacomik`

### Issue: Uninstall old version first
```bash
adb uninstall app.komikku  # Remove Komikku
adb install Nana-Comik-master-r{commit}.apk  # Install Nana Comik
```

---

## ✨ Features Included

✅ PDF reading support (local sources)
✅ New branding (Nana Comik)
✅ New package name (side-by-side installation)
✅ Proper APK signing (release-quality)
✅ CI/CD automation (GitHub Actions)
✅ Multiple build variants

---

## 📚 Documentation Files

- **COMPLETE_SETUP.md** ← START HERE for commands
- **SIGNING_SETUP.md** - Detailed setup walkthrough
- **NANA_COMIK_SETUP.md** - Technical details of changes
- **generate-keystore.sh** - Script to regenerate keystore

---

## 🎯 Status

| Task | Status |
|------|--------|
| Package rename to app.nanacomik | ✅ |
| App display name to Nana Comik | ✅ |
| Signing configuration in build.gradle | ✅ |
| Workflow updates for signing | ✅ |
| Documentation & setup guides | ✅ |
| **Pending: Execute local setup commands** | ⏳ |
| **Pending: Add GitHub Secrets** | ⏳ |
| **Pending: First signed build & install** | ⏳ |

---

## 🔄 What Happens When You Push

1. GitHub Actions triggers `build_push.yml`
2. Reads base64-encoded keystore from `KEYSTORE_FILE` secret
3. Decodes it to `app/nanacomik.keystore`
4. Builds app with signing credentials
5. Generates signed APK: `Nana-Comik-master-r{commit}.apk`
6. Uploads as artifact for download

Same process for release builds via `build_release.yml`

---

**Ready to start? → Read COMPLETE_SETUP.md next!**
