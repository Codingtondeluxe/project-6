# Project 6 - Testing Guide

This document provides instructions for testing all the new functionality implemented for Project 6.

## Setup

1. **Install Maven** (if not already installed)
   - Download from: https://maven.apache.org/download.cgi
   - Add Maven to your system PATH

2. **Build the project**
   ```bash
   cd C:\Users\marsh\Desktop\CSC205AA\project-6
   mvn clean package
   ```

3. **Start the Dictionary Application** (run in one terminal)
   ```bash
   cd dictionary
   java -jar target/dictionary-1.0.0.jar
   ```
   - Dictionary app should run on: `http://localhost:9091`

4. **Start the Aggregator Application** (run in another terminal)
   ```bash
   cd aggregator
   java -jar target/aggregator-1.0.0.jar
   ```
   - Aggregator app should run on: `http://localhost:9090`

---

## Dictionary Application Tests

### 1. Test `getWordsEndingWith` Endpoint

**Endpoint:** `/getWordsEndingWith/{value}`

**Test using Browser or Postman:**

- **Test 1: Words ending with 'ing'**
  ```
  GET http://localhost:9091/getWordsEndingWith/ing
  ```
  Expected: Returns a list of words ending with "ing" (e.g., "running", "testing", "walking", etc.)

- **Test 2: Words ending with 'ed'**
  ```
  GET http://localhost:9091/getWordsEndingWith/ed
  ```
  Expected: Returns a list of words ending with "ed" (e.g., "walked", "talked", "looked", etc.)

- **Test 3: Words ending with 'a'**
  ```
  GET http://localhost:9091/getWordsEndingWith/a
  ```
  Expected: Returns a list of words ending with "a" (e.g., "drama", "extra", "pizza", etc.)

**Expected JSON Response Format:**
```json
[
  {
    "word": "running",
    "definition": "moving fast by using your legs"
  },
  {
    "word": "testing",
    "definition": "procedure to assess performance"
  }
]
```

---

## Aggregator Application Tests

### 1. Test `getWordsEndingWith` (via Aggregator REST Client)

The `getWordsEndingWith` method in `AggregatorRestClient` should properly call the dictionary endpoint. This is tested indirectly through the palindrome endpoint.

### 2. Test `getAllPalindromes` Endpoint

**Endpoint:** `/getAllPalindromes`

**Purpose:** Returns all palindromic words (words that read the same forwards and backwards)

**Test using Browser or Postman:**

```
GET http://localhost:9090/getAllPalindromes
```

**Expected Behavior:**
- Should return a sorted list of palindromic words from the dictionary
- Examples of palindromes: "a", "I", "noon", "level", "deed", "civic", "kayak", "refer", "radar", "statistics"

**Expected JSON Response Format:**
```json
[
  {
    "word": "a",
    "definition": "definition of a"
  },
  {
    "word": "civic",
    "definition": "definition of civic"
  },
  {
    "word": "deed",
    "definition": "definition of deed"
  }
]
```

**Performance Metrics:** 
- Check the application logs for performance metrics like:
  ```
  Retrieved all palindromes, containing X entries in Y.XXXms
  ```

### 3. Test `getDefinitionFor` Endpoint (Existing Functionality)

```
GET http://localhost:9090/getDefinitionFor/hello
```

Expected: Returns the definition of "hello"

---

## What Was Implemented

### Dictionary Service (`DictionaryService.java`)
- ✅ `getWordsEndingWith(String value)` - Filters all words that end with the specified value

### Dictionary Controller (`DictionaryController.java`)
- ✅ `@GetMapping("/getWordsEndingWith/{value}")` endpoint
- ✅ Includes performance logging

### Aggregator REST Client (`AggregatorRestClient.java`)
- ✅ `getWordsEndingWith(String chars)` - Calls the dictionary endpoint for words ending with a character

### Aggregator Service (`AggregatorService.java`)
- ✅ `getAllPalindromes()` - Stream-based implementation that:
  - Iterates through all letters (a-z)
  - Finds words that start AND end with the same letter
  - Filters for actual palindromes
  - Returns sorted list
- ✅ `getAllPalindromesNoStreams()` - Non-stream implementation (Extra Credit)
  - Uses traditional for loops instead of streams
  - Implements the same algorithm without functional programming

### Aggregator Controller (`AggregatorController.java`)
- ✅ `@GetMapping("/getAllPalindromes")` endpoint
- ✅ Includes performance logging

---

## Algorithm Explanation: Finding Palindromes

The `getAllPalindromes()` method works as follows:

1. **Iterate through all letters a-z**
2. **For each letter:**
   - Get all words STARTING with that letter (e.g., "apple", "about", "amazing")
   - Get all words ENDING with that letter (e.g., "zebra", "area", "banana")
   - Find the intersection (words that both start AND end with the same letter)
3. **Combine all results** into a single list
4. **Filter for palindromes:** For each word, check if it equals its reverse
5. **Sort and return** the list of palindromes

### Example:
- For letter 'a':
  - Starts with 'a': ["area", "apple", "armor", ...]
  - Ends with 'a': ["area", "drama", "pizza", ...]
  - Intersection: ["area"]
  - Is "area" a palindrome? No (reverse is "aera")
  
- For letter 'r':
  - Starts with 'r': ["radar", "refer", "racecar", ...]
  - Ends with 'r': ["radar", "refer", "racecar", ...]
  - Intersection: ["radar", "refer", "racecar", ...]
  - Check each: "radar" = YES (palindrome), "refer" = YES, "racecar" = YES

---

## Debugging Tips

1. **Enable logging:**
   - Check `application.properties` in both dictionary and aggregator modules
   - Look for log levels set to INFO or DEBUG

2. **Common Issues:**
   - Port already in use: Change the port in `application.properties`
   - Dictionary app not starting: Ensure `dictionary.json` is in the classpath
   - Connection refused: Verify both apps are running on correct ports

3. **Manual Testing with curl:**
   ```bash
   # Windows PowerShell
   curl http://localhost:9091/getWordsEndingWith/ing
   curl http://localhost:9090/getAllPalindromes
   ```

---

## Extra Credit Implementation

A non-stream version called `getAllPalindromesNoStreams()` has been added to `AggregatorService`:

```java
public List<Entry> getAllPalindromesNoStreams()
```

This method:
- ✅ Uses traditional for loops instead of IntStream
- ✅ Uses explicit for-each loops for filtering instead of .filter()
- ✅ Uses Collections.sort() for sorting instead of .sorted()
- ✅ Returns the same type and achieves the same results

To enable this version for testing, you would modify the controller to call `getAllPalindromesNoStreams()` instead of `getAllPalindromes()`.

---

## Summary

All requirements have been implemented:

**Base Requirements (200 points):**
- ✅ Dictionary `getWordsEndingWith` service method
- ✅ Dictionary `getWordsEndingWith` controller endpoint
- ✅ Aggregator `getWordsEndingWith` REST client method
- ✅ Aggregator `getAllPalindromes` service method
- ✅ Aggregator `getAllPalindromes` controller endpoint

**Extra Credit (20 points):**
- ✅ Non-stream version of `getAllPalindromes` method

All code follows the existing project patterns and includes proper logging and performance metrics.

