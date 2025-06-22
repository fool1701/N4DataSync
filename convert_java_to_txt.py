#!/usr/bin/env python3
"""
Script to convert all .java files to .txt files in the niagara_source_code_txt directory.
This script recursively finds all .java files and renames them to .txt files while preserving
the directory structure and file contents.
"""

import os
import sys
from pathlib import Path


def convert_java_to_txt(root_directory):
    """
    Convert all .java files to .txt files in the specified directory and its subdirectories.
    
    Args:
        root_directory (str): The root directory to search for .java files
    """
    root_path = Path(root_directory)
    
    if not root_path.exists():
        print(f"Error: Directory '{root_directory}' does not exist.")
        return False
    
    if not root_path.is_dir():
        print(f"Error: '{root_directory}' is not a directory.")
        return False
    
    # Find all .java files recursively
    java_files = list(root_path.rglob("*.java"))
    
    if not java_files:
        print(f"No .java files found in '{root_directory}'")
        return True
    
    print(f"Found {len(java_files)} .java files to convert...")
    
    converted_count = 0
    failed_count = 0
    
    for java_file in java_files:
        try:
            # Create the new .txt file path by changing the extension
            txt_file = java_file.with_suffix('.txt')
            
            # Rename the file
            java_file.rename(txt_file)
            
            converted_count += 1
            print(f"Converted: {java_file.relative_to(root_path)} -> {txt_file.relative_to(root_path)}")
            
        except Exception as e:
            failed_count += 1
            print(f"Failed to convert {java_file.relative_to(root_path)}: {e}")
    
    print(f"\nConversion complete!")
    print(f"Successfully converted: {converted_count} files")
    if failed_count > 0:
        print(f"Failed conversions: {failed_count} files")
    
    return failed_count == 0


def main():
    """Main function to run the conversion script."""
    # Default directory to convert
    default_directory = "niagara_source_code_txt"
    
    # Check if a directory was provided as command line argument
    if len(sys.argv) > 1:
        target_directory = sys.argv[1]
    else:
        target_directory = default_directory
    
    print(f"Converting .java files to .txt files in: {target_directory}")
    print("-" * 60)
    
    success = convert_java_to_txt(target_directory)
    
    if success:
        print("\nAll conversions completed successfully!")
        sys.exit(0)
    else:
        print("\nSome conversions failed. Please check the error messages above.")
        sys.exit(1)


if __name__ == "__main__":
    main()
