# PDF Text Extraction Comparison

This project demonstrates 6 different Python libraries for extracting text from PDF files while maintaining structure and layout. All scripts were tested on the `docDeveloper.pdf` file (243 pages).

## Libraries Tested

1. **pdfplumber** - Excellent for layout preservation and table extraction
2. **pypdf** - Modern PyPDF2 successor with clean extraction
3. **PyPDF2** - Classic library with good compatibility
4. **PyPDF4** - PyPDF2 fork (had issues with encrypted PDFs)
5. **PyMuPDF (Fitz)** - Powerful library with multiple extraction methods
6. **pdfminer.six** - Most detailed layout analysis

## Results Summary

| Library | Status | File Size (KB) | Lines | Text Quality |
|---------|--------|----------------|-------|--------------|
| pdfplumber | ✓ SUCCESS | 1,406.2 | 17,114 | 96.04% |
| pypdf | ✓ SUCCESS | 577.4 | 11,304 | 90.59% |
| PyPDF2 | ✓ SUCCESS | 566.2 | 8,983 | 90.44% |
| PyPDF4 | ✗ FAILED | N/A | N/A | N/A |
| PyMuPDF | ✓ SUCCESS | 1,736.3 | 49,947 | 92.89% |
| pdfminer.six | ✓ SUCCESS | 2,480.3 | 27,557 | 95.95% |

**Success Rate: 5/6 (83%)**

## Key Findings

### Content Extraction
- **pdfminer.six** extracted the most content (2.5MB, 684k words)
- **PyMuPDF** provided multiple extraction methods in one script
- **pdfplumber** had the best text quality ratio (96.04%)
- **pypdf/PyPDF2** provided clean, compact extraction

### Keyword Detection
All successful libraries detected key terms from the developer guide:
- Niagara: 818-2454 occurrences
- Developer: 309-927 occurrences  
- API: 198-594 occurrences
- Java classes: 151-453 detected
- Method calls: 600-1806 detected

### Layout Preservation
- **pdfplumber**: Best for tables and structured data
- **pdfminer.six**: Most detailed positional information
- **PyMuPDF**: Multiple extraction approaches (text, HTML, blocks)
- **pypdf/PyPDF2**: Basic but reliable text flow

## Files Created

### Extraction Scripts
- `extract_with_pdfplumber.py` - Uses pdfplumber library
- `extract_with_pypdf.py` - Uses pypdf library
- `extract_with_pypdf2.py` - Uses PyPDF2 library
- `extract_with_pypdf4.py` - Uses PyPDF4 library (failed on encrypted PDF)
- `extract_with_pymupdf.py` - Uses PyMuPDF/Fitz library
- `extract_with_pdfminer.py` - Uses pdfminer.six library

### Output Files
- `extracted_text_pdfplumber.txt` - pdfplumber output
- `extracted_text_pypdf.txt` - pypdf output
- `extracted_text_pypdf2.txt` - PyPDF2 output
- `extracted_text_pymupdf.txt` - PyMuPDF output
- `extracted_text_pdfminer.txt` - pdfminer.six output

### Utility Scripts
- `run_all_extractions.py` - Master script to run all extractions
- `compare_results.py` - Compare extraction results
- `test_extractions.py` - Test extraction quality
- `README.md` - This documentation

## Installation

All required packages are automatically installed by the master script:

```bash
py -m pip install pdfplumber pypdf PyPDF2 PyPDF4 PyMuPDF pdfminer.six PyCryptodome
```

## Usage

### Run Individual Scripts
```bash
py extract_with_pdfplumber.py
py extract_with_pypdf.py
py extract_with_pypdf2.py
py extract_with_pymupdf.py
py extract_with_pdfminer.py
```

### Run All Extractions
```bash
py run_all_extractions.py
```

### Compare Results
```bash
py compare_results.py
py test_extractions.py
```

## Recommendations

**Choose based on your needs:**

- **For tables and layout preservation**: pdfplumber
- **For maximum content extraction**: pdfminer.six
- **For multiple extraction methods**: PyMuPDF
- **For simple, clean text**: pypdf or PyPDF2
- **For encrypted PDFs**: Avoid PyPDF4, use others with proper decryption

## Notes

- PyPDF4 failed on the encrypted PDF despite decryption attempts
- All other libraries successfully handled the 243-page document
- pdfminer.six extracted the most comprehensive content
- pdfplumber provided the best balance of quality and layout preservation
- Processing time varied from ~30 seconds (pypdf) to ~2 minutes (pdfminer.six)

## Technical Details

- **PDF**: docDeveloper.pdf (243 pages, encrypted)
- **Python**: 3.13.3
- **Platform**: Windows
- **Total extraction time**: ~10 minutes for all methods
- **Output formats**: Plain text with preserved structure
