/* Generated By:JavaCC: Do not edit this line. ARQParserConstants.java */
/*
 * (c) Copyright 2004, 2005, 2006, 2007, 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 */

package com.hp.hpl.jena.sparql.lang.arq ;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ARQParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int WS = 6;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 7;
  /** RegularExpression Id. */
  int IRIref = 8;
  /** RegularExpression Id. */
  int PNAME_NS = 9;
  /** RegularExpression Id. */
  int PNAME_LN = 10;
  /** RegularExpression Id. */
  int BLANK_NODE_LABEL = 11;
  /** RegularExpression Id. */
  int VAR1 = 12;
  /** RegularExpression Id. */
  int VAR2 = 13;
  /** RegularExpression Id. */
  int LANGTAG = 14;
  /** RegularExpression Id. */
  int A2Z = 15;
  /** RegularExpression Id. */
  int A2ZN = 16;
  /** RegularExpression Id. */
  int KW_A = 17;
  /** RegularExpression Id. */
  int BASE = 18;
  /** RegularExpression Id. */
  int PREFIX = 19;
  /** RegularExpression Id. */
  int SELECT = 20;
  /** RegularExpression Id. */
  int DISTINCT = 21;
  /** RegularExpression Id. */
  int REDUCED = 22;
  /** RegularExpression Id. */
  int DESCRIBE = 23;
  /** RegularExpression Id. */
  int CONSTRUCT = 24;
  /** RegularExpression Id. */
  int ASK = 25;
  /** RegularExpression Id. */
  int LIMIT = 26;
  /** RegularExpression Id. */
  int OFFSET = 27;
  /** RegularExpression Id. */
  int ORDER = 28;
  /** RegularExpression Id. */
  int BY = 29;
  /** RegularExpression Id. */
  int ASC = 30;
  /** RegularExpression Id. */
  int DESC = 31;
  /** RegularExpression Id. */
  int NAMED = 32;
  /** RegularExpression Id. */
  int FROM = 33;
  /** RegularExpression Id. */
  int WHERE = 34;
  /** RegularExpression Id. */
  int AND = 35;
  /** RegularExpression Id. */
  int GRAPH = 36;
  /** RegularExpression Id. */
  int OPTIONAL = 37;
  /** RegularExpression Id. */
  int UNION = 38;
  /** RegularExpression Id. */
  int SERVICE = 39;
  /** RegularExpression Id. */
  int LET = 40;
  /** RegularExpression Id. */
  int EXISTS = 41;
  /** RegularExpression Id. */
  int NOT = 42;
  /** RegularExpression Id. */
  int NOTEXISTS = 43;
  /** RegularExpression Id. */
  int UNSAID = 44;
  /** RegularExpression Id. */
  int AS = 45;
  /** RegularExpression Id. */
  int GROUP = 46;
  /** RegularExpression Id. */
  int HAVING = 47;
  /** RegularExpression Id. */
  int AGG = 48;
  /** RegularExpression Id. */
  int COUNT = 49;
  /** RegularExpression Id. */
  int MIN = 50;
  /** RegularExpression Id. */
  int MAX = 51;
  /** RegularExpression Id. */
  int SUM = 52;
  /** RegularExpression Id. */
  int AVG = 53;
  /** RegularExpression Id. */
  int STDDEV = 54;
  /** RegularExpression Id. */
  int FETCH = 55;
  /** RegularExpression Id. */
  int FILTER = 56;
  /** RegularExpression Id. */
  int BOUND = 57;
  /** RegularExpression Id. */
  int COALESCE = 58;
  /** RegularExpression Id. */
  int IN = 59;
  /** RegularExpression Id. */
  int NOT_IN = 60;
  /** RegularExpression Id. */
  int IF = 61;
  /** RegularExpression Id. */
  int BNODE = 62;
  /** RegularExpression Id. */
  int IRI = 63;
  /** RegularExpression Id. */
  int URI = 64;
  /** RegularExpression Id. */
  int CAST = 65;
  /** RegularExpression Id. */
  int CALL = 66;
  /** RegularExpression Id. */
  int STR = 67;
  /** RegularExpression Id. */
  int STRLANG = 68;
  /** RegularExpression Id. */
  int STRDT = 69;
  /** RegularExpression Id. */
  int DTYPE = 70;
  /** RegularExpression Id. */
  int LANG = 71;
  /** RegularExpression Id. */
  int LANGMATCHES = 72;
  /** RegularExpression Id. */
  int IS_URI = 73;
  /** RegularExpression Id. */
  int IS_IRI = 74;
  /** RegularExpression Id. */
  int IS_BLANK = 75;
  /** RegularExpression Id. */
  int IS_LITERAL = 76;
  /** RegularExpression Id. */
  int REGEX = 77;
  /** RegularExpression Id. */
  int SAME_TERM = 78;
  /** RegularExpression Id. */
  int TRUE = 79;
  /** RegularExpression Id. */
  int FALSE = 80;
  /** RegularExpression Id. */
  int MODIFY = 81;
  /** RegularExpression Id. */
  int INSERT = 82;
  /** RegularExpression Id. */
  int DELETE = 83;
  /** RegularExpression Id. */
  int DATA = 84;
  /** RegularExpression Id. */
  int ADD = 85;
  /** RegularExpression Id. */
  int REMOVE = 86;
  /** RegularExpression Id. */
  int LOAD = 87;
  /** RegularExpression Id. */
  int CLEAR = 88;
  /** RegularExpression Id. */
  int CREATE = 89;
  /** RegularExpression Id. */
  int SILENT = 90;
  /** RegularExpression Id. */
  int DROP = 91;
  /** RegularExpression Id. */
  int INTO = 92;
  /** RegularExpression Id. */
  int DFT = 93;
  /** RegularExpression Id. */
  int WITH = 94;
  /** RegularExpression Id. */
  int DIGITS = 95;
  /** RegularExpression Id. */
  int INTEGER = 96;
  /** RegularExpression Id. */
  int DECIMAL = 97;
  /** RegularExpression Id. */
  int DOUBLE = 98;
  /** RegularExpression Id. */
  int INTEGER_POSITIVE = 99;
  /** RegularExpression Id. */
  int DECIMAL_POSITIVE = 100;
  /** RegularExpression Id. */
  int DOUBLE_POSITIVE = 101;
  /** RegularExpression Id. */
  int INTEGER_NEGATIVE = 102;
  /** RegularExpression Id. */
  int DECIMAL_NEGATIVE = 103;
  /** RegularExpression Id. */
  int DOUBLE_NEGATIVE = 104;
  /** RegularExpression Id. */
  int EXPONENT = 105;
  /** RegularExpression Id. */
  int QUOTE_3D = 106;
  /** RegularExpression Id. */
  int QUOTE_3S = 107;
  /** RegularExpression Id. */
  int ECHAR = 108;
  /** RegularExpression Id. */
  int STRING_LITERAL1 = 109;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 110;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG1 = 111;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG2 = 112;
  /** RegularExpression Id. */
  int LPAREN = 113;
  /** RegularExpression Id. */
  int RPAREN = 114;
  /** RegularExpression Id. */
  int NIL = 115;
  /** RegularExpression Id. */
  int LBRACE = 116;
  /** RegularExpression Id. */
  int RBRACE = 117;
  /** RegularExpression Id. */
  int LBRACKET = 118;
  /** RegularExpression Id. */
  int RBRACKET = 119;
  /** RegularExpression Id. */
  int ANON = 120;
  /** RegularExpression Id. */
  int SEMICOLON = 121;
  /** RegularExpression Id. */
  int COMMA = 122;
  /** RegularExpression Id. */
  int DOT = 123;
  /** RegularExpression Id. */
  int EQ = 124;
  /** RegularExpression Id. */
  int NE = 125;
  /** RegularExpression Id. */
  int GT = 126;
  /** RegularExpression Id. */
  int LT = 127;
  /** RegularExpression Id. */
  int LE = 128;
  /** RegularExpression Id. */
  int GE = 129;
  /** RegularExpression Id. */
  int BANG = 130;
  /** RegularExpression Id. */
  int TILDE = 131;
  /** RegularExpression Id. */
  int COLON = 132;
  /** RegularExpression Id. */
  int SC_OR = 133;
  /** RegularExpression Id. */
  int SC_AND = 134;
  /** RegularExpression Id. */
  int PLUS = 135;
  /** RegularExpression Id. */
  int MINUS = 136;
  /** RegularExpression Id. */
  int STAR = 137;
  /** RegularExpression Id. */
  int SLASH = 138;
  /** RegularExpression Id. */
  int DATATYPE = 139;
  /** RegularExpression Id. */
  int AT = 140;
  /** RegularExpression Id. */
  int ASSIGN = 141;
  /** RegularExpression Id. */
  int VBAR = 142;
  /** RegularExpression Id. */
  int CARAT = 143;
  /** RegularExpression Id. */
  int FPATH = 144;
  /** RegularExpression Id. */
  int RPATH = 145;
  /** RegularExpression Id. */
  int QMARK = 146;
  /** RegularExpression Id. */
  int PN_CHARS_BASE = 147;
  /** RegularExpression Id. */
  int PN_CHARS_U = 148;
  /** RegularExpression Id. */
  int PN_CHARS = 149;
  /** RegularExpression Id. */
  int PN_PREFIX = 150;
  /** RegularExpression Id. */
  int PN_LOCAL = 151;
  /** RegularExpression Id. */
  int VARNAME = 152;
  /** RegularExpression Id. */
  int UNKNOWN = 153;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<WS>",
    "<SINGLE_LINE_COMMENT>",
    "<IRIref>",
    "<PNAME_NS>",
    "<PNAME_LN>",
    "<BLANK_NODE_LABEL>",
    "<VAR1>",
    "<VAR2>",
    "<LANGTAG>",
    "<A2Z>",
    "<A2ZN>",
    "\"a\"",
    "\"base\"",
    "\"prefix\"",
    "\"select\"",
    "\"distinct\"",
    "\"reduced\"",
    "\"describe\"",
    "\"construct\"",
    "\"ask\"",
    "\"limit\"",
    "\"offset\"",
    "\"order\"",
    "\"by\"",
    "\"asc\"",
    "\"desc\"",
    "\"named\"",
    "\"from\"",
    "\"where\"",
    "\"and\"",
    "\"graph\"",
    "\"optional\"",
    "\"union\"",
    "\"service\"",
    "\"let\"",
    "\"exists\"",
    "\"not\"",
    "<NOTEXISTS>",
    "\"unsaid\"",
    "\"as\"",
    "\"group\"",
    "\"having\"",
    "\"agg\"",
    "\"count\"",
    "\"min\"",
    "\"max\"",
    "\"sum\"",
    "\"avg\"",
    "\"stdev\"",
    "\"fetch\"",
    "\"filter\"",
    "\"bound\"",
    "\"coalesce\"",
    "\"in\"",
    "<NOT_IN>",
    "\"if\"",
    "\"bnode\"",
    "\"iri\"",
    "\"uri\"",
    "\"cast\"",
    "\"call\"",
    "\"str\"",
    "\"strlang\"",
    "\"strdt\"",
    "\"datatype\"",
    "\"lang\"",
    "\"langmatches\"",
    "\"isURI\"",
    "\"isIRI\"",
    "\"isBlank\"",
    "\"isLiteral\"",
    "\"regex\"",
    "\"sameTerm\"",
    "\"true\"",
    "\"false\"",
    "\"modify\"",
    "\"insert\"",
    "\"delete\"",
    "\"data\"",
    "\"add\"",
    "\"remove\"",
    "\"load\"",
    "\"clear\"",
    "\"create\"",
    "\"silent\"",
    "\"drop\"",
    "\"into\"",
    "\"default\"",
    "\"with\"",
    "<DIGITS>",
    "<INTEGER>",
    "<DECIMAL>",
    "<DOUBLE>",
    "<INTEGER_POSITIVE>",
    "<DECIMAL_POSITIVE>",
    "<DOUBLE_POSITIVE>",
    "<INTEGER_NEGATIVE>",
    "<DECIMAL_NEGATIVE>",
    "<DOUBLE_NEGATIVE>",
    "<EXPONENT>",
    "\"\\\"\\\"\\\"\"",
    "\"\\\'\\\'\\\'\"",
    "<ECHAR>",
    "<STRING_LITERAL1>",
    "<STRING_LITERAL2>",
    "<STRING_LITERAL_LONG1>",
    "<STRING_LITERAL_LONG2>",
    "\"(\"",
    "\")\"",
    "<NIL>",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "<ANON>",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\"!=\"",
    "\">\"",
    "\"<\"",
    "\"<=\"",
    "\">=\"",
    "\"!\"",
    "\"~\"",
    "\":\"",
    "\"||\"",
    "\"&&\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"^^\"",
    "\"@\"",
    "\":=\"",
    "\"|\"",
    "\"^\"",
    "\"->\"",
    "\"<-\"",
    "\"?\"",
    "<PN_CHARS_BASE>",
    "<PN_CHARS_U>",
    "<PN_CHARS>",
    "<PN_PREFIX>",
    "<PN_LOCAL>",
    "<VARNAME>",
    "<UNKNOWN>",
  };

}
