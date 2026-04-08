# 🔐 File Packer and Unpacker with Encryption — Java Utility Project

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![Type](https://img.shields.io/badge/Type-File%20Utility%20Tool-red)
![Library](https://img.shields.io/badge/Library-javax.crypto-yellow)
![Feature](https://img.shields.io/badge/Feature-AES%20Encryption-orange)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)

A Java-based file utility tool that packs multiple files into a single archive and unpacks them back to their original form — with full AES encryption support and complete metadata preservation. File names, sizes, and structure are retained accurately through every pack and unpack cycle.

---

## 🧾 Problem Statement :-

Developers and system users often need to bundle multiple files into a single transferable unit while keeping sensitive data secure. Manually archiving files is tedious, and most simple archiving tools skip encryption or lose metadata during the process. This project automates the complete pack-encrypt-unpack pipeline — bundling files into one secure archive, encrypting the content with AES, and unpacking everything back with all metadata intact — using pure Java with no external dependencies.

---

## 🏗️ Project Structure :-

```
File_Packer_Unpacker_Encryption/
│
├── FilePacker.java             # Core packing logic — reads files, writes packed archive
├── FileUnpacker.java           # Core unpacking logic — reads archive, restores original files
├── EncryptionUtil.java         # AES encryption and decryption utility class
├── Main.java                   # Entry point — handles CLI arguments and orchestrates workflow
│
├── packed/                     # Output folder for packed and encrypted archive files
│   ├── archive_2026-03-10.pack
│   └── archive_2026-03-11_encrypted.pack
│
├── unpacked/                   # Output folder for restored original files after unpacking
│   ├── document.txt
│   └── report.pdf
│
├── .env                        # Environment variables (encryption keys — do not push to GitHub)
└── README.md
```

---

## ✨ Key Features :-

| Feature | Description |
|---------|-------------|
| File Packing | Bundles multiple input files into a single structured archive file |
| File Unpacking | Restores all original files from the archive with full accuracy |
| AES Encryption | Encrypts the packed archive using AES symmetric encryption |
| AES Decryption | Decrypts the archive before unpacking to recover original contents |
| Metadata Preservation | Retains original file names, sizes, and structure through every cycle |
| Multi-File Support | Packs and unpacks any number of files in a single operation |
| Custom Output Directory | Saves packed archives and unpacked files to user-specified directories |
| CLI Interface | Fully operable from the command line with clear argument handling |
| Error Handling | Handles missing files, wrong keys, and I/O errors gracefully |
| Pure Java Implementation | No external libraries required — uses only Java standard library |

---

## 🛠️ Functions Overview :- 

| Function | File | Description |
|----------|------|-------------|
| `packFiles` | FilePacker.java | Reads input files, writes metadata headers and content into one archive |
| `unpackFiles` | FileUnpacker.java | Reads the archive, parses metadata headers, and restores each file |
| `encrypt` | EncryptionUtil.java | Encrypts byte data using AES with the provided secret key |
| `decrypt` | EncryptionUtil.java | Decrypts AES-encrypted byte data back to original content |
| `generateKey` | EncryptionUtil.java | Generates a secure AES key from a user-provided passphrase |
| `main` | Main.java | Entry point — parses CLI arguments and triggers pack or unpack workflow |

---

## 🔁 How It Works — Step by Step :-

**Step 1 — Input Collection**
The user provides a list of file paths to pack. The `Main.java` entry point reads the CLI arguments and validates that all input files exist and are accessible before proceeding.

**Step 2 — File Packing**
`FilePacker.java` reads each input file and writes a structured binary archive. Each file's entry starts with a metadata header — storing the original file name and byte size — followed immediately by the raw file content bytes. All entries are written sequentially into one `.pack` archive file.

**Step 3 — AES Encryption (Optional)**
If encryption is enabled, `EncryptionUtil.java` takes the completed archive bytes and encrypts them using AES symmetric encryption. A secure AES key is derived from the user-provided passphrase. The encrypted output is written to the final archive file.

**Step 4 — Unpacking**
`FileUnpacker.java` reads the archive file. If it is encrypted, decryption is performed first using the same passphrase. The unpacker then parses each metadata header to retrieve the original file name and size, reads exactly that many bytes from the archive stream, and writes each file to the output directory.

**Step 5 — Metadata Restoration**
All files are restored to their original names in the specified output directory. File sizes are verified against the stored metadata to confirm that every byte was unpacked correctly and no data was lost or corrupted.

---

## 🗂️ Archive File Structure :-

Each `.pack` file generated by this tool follows a structured binary format:

| Section | Contents |
|---------|----------|
| Archive Header | Magic bytes identifying this as a valid pack file |
| Entry Header (per file) | Original file name length, file name bytes, and original file size in bytes |
| Entry Content (per file) | Raw file content bytes of exactly the recorded size |
| Encryption Wrapper | AES-encrypted envelope around all of the above (when encryption is enabled) |

---

## 📌 Command Line Usage :-

```bash
# Show help
java Main --help

# Pack files without encryption
java Main pack output/archive.pack file1.txt file2.pdf file3.jpg

# Pack files with AES encryption
java Main pack output/archive.pack --encrypt mySecretPassphrase file1.txt file2.pdf

# Unpack a plain archive
java Main unpack output/archive.pack ./unpacked/

# Unpack an encrypted archive
java Main unpack output/archive.pack ./unpacked/ --decrypt mySecretPassphrase
```

Press `Ctrl + C` to cancel any running operation.

---

## 🔒 Encryption Configuration :-

The encryption feature uses AES (Advanced Encryption Standard) with a key derived from a user-provided passphrase via PBKDF2 key derivation. To use encryption:

1. Choose a strong passphrase — this is required both to encrypt and to decrypt
2. Pass the passphrase as the `--encrypt` argument when packing
3. Use the same passphrase with `--decrypt` when unpacking
4. Never share or commit your passphrase — store it securely in your `.env` file

> **Note:** If the wrong passphrase is used during decryption, the tool will throw an error and halt unpacking safely to prevent corrupted output.

---

## ⚙️ Tech Stack :-

- Java 17+
- `javax.crypto` — AES encryption and decryption
- `java.security` — Key generation and PBKDF2 key derivation
- `java.io` — File reading, writing, and binary stream handling
- `java.nio.file` — File path resolution, directory creation, and metadata access
- `java.util` — Argument parsing, collections, and byte buffer handling

---

## ▶️ How to Run :-

1. Clone this repository
2. Compile all Java source files:
   ```bash
   javac *.java
   ```
3. Run the packer:
   ```bash
   java Main pack packed/archive.pack file1.txt file2.pdf
   ```
4. Run the unpacker:
   ```bash
   java Main unpack packed/archive.pack ./unpacked/
   ```

---

## 📘 Key Concepts Covered :-

- Binary File I/O using `FileInputStream`, `FileOutputStream`, and `DataOutputStream`
- Structured Archive Format Design with metadata headers and content entries
- AES Symmetric Encryption and Decryption using `javax.crypto.Cipher`
- Secure Key Derivation from Passphrases using PBKDF2 with HMAC-SHA256
- Metadata Preservation — storing and restoring file names and byte sizes
- Multi-File Aggregation into a single sequential binary archive
- Stream-Based File Processing without loading entire files into memory at once
- Exception Handling for `IOException`, `GeneralSecurityException`, and file-not-found cases
- Command Line Argument Parsing using `String[] args` and conditional branching
- Directory Management — auto-creating output directories if they do not exist

---

## 👩‍💻 Author :-

**Laxmi Shankar Aade**

Built as a Java File Utility Project to understand binary file I/O, archive format design, AES encryption and decryption, metadata preservation across pack and unpack cycles, and command-line driven file processing using core Java standard libraries.
