// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;

import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;
import com.google.codeu.mathlang.core.tokens.NameToken;
import com.google.codeu.mathlang.core.tokens.NumberToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;


// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.


public final class MyTokenReader implements TokenReader {
	
	// input for token reader 
	private String source; 
	// keeps track of where we are in parsing through source 
	private int currentIndex = 0; 

  public MyTokenReader(String source) {
	  this.source = source; 
  }

  @Override
  public Token next() throws IOException {
	  
		// if we have reached the end, return null 
		if (source.length() - currentIndex < 0) {
			return null;
		}
		
		// ignore all spaces in source  
		while (source.charAt(currentIndex) == ' ') {
			currentIndex++;
		}
		
		
		// looks at the next token in source 
		String next = nextToken(); 
		
		if (isNumber(next)) {
			return new NumberToken(Double.parseDouble(next));
		}
		if (isName(next)) {
			return new NameToken(next);
		}
		if (isSymbol(next)) {
			return new SymbolToken(next.charAt(0));
		}
		//if it is not any of the above, it must be a string 
		return new StringToken(next);
  }
  
  // Helper functions 
  
  // nextToken()
  // returns the next valid string to turn into a token  
  private String nextToken(){ 
		String next = "";
		
		// if it is a string starts with " 
		// return the string until the next " 
		if (source.charAt(currentIndex) == '"'){
			
			// while the string has not ended, continue adding to next string 
			while (source.length() - currentIndex > 0 &&  
					source.charAt(currentIndex) != '\n' &&
					source.charAt(currentIndex) != '"' ) {
				next += source.charAt(currentIndex);
				currentIndex++;
			}
		} 
		
		// if the string is not encased in quotes, 
		// return the string up to the next space 
		else {
			while (source.length() - currentIndex > 0 &&  
					source.charAt(currentIndex) != '\n' &&
					source.charAt(currentIndex) != ' ') {
				// if the character is a symbol but there is already input in next, 
				// return next 
				if (isSymbol(source.substring(currentIndex, currentIndex + 1)) && !next.isEmpty()){
					return next; 
				}
				else if (isSymbol(source.substring(currentIndex, currentIndex + 1)) && next.isEmpty()){
					currentIndex++;
					return source.substring(currentIndex, currentIndex + 1); 
				}
			}
				next += source.charAt(currentIndex);
				currentIndex++;
		}		
		
		currentIndex++; 
		return next; 
  }
  

  private boolean isName(String str){ 
	  return (str.equals("let") || str.equals("note") || str.equals("print"));
  }
  
  private boolean isNumber(String str){ 
	  return (Character.isDigit(str.charAt(0))); 
  }
  
  private boolean isSymbol(String str){ 
	  return (str.equals('=') || str.equals('+') || str.equals('-') || str.equals(';'));
  }

}