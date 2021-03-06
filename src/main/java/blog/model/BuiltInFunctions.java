/*
 * Copyright (c) 2005 - 2012, Regents of the University of California
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 * 
 * * Neither the name of the University of California, Berkeley nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package blog.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import blog.common.numerical.MatrixFactory;
import blog.common.numerical.MatrixLib;
import blog.type.Timestep;

/**
 * Class with static methods and constants for built-in non-random functions
 * (including built-in constants). This class cannot be instantiated.
 * 
 * @author unknown
 * @author leili
 * @date 2/11/2014
 */
public class BuiltInFunctions {
  /**
   * internal names for builtin functions, these are not supposed to be called
   * by users
   */
  public static final String PLUS_NAME = "__PLUS";
  public static final String MINUS_NAME = "__MINUS";
  public static final String MULT_NAME = "__MULT";
  public static final String DIV_NAME = "__DIV";
  public static final String MOD_NAME = "__MOD";
  public static final String POWER_NAME = "__POWER";
  public static final String ARRAY_MAT_ELEMENT_NAME = "__ARRAY_MAT_ELEMENT";
  public static final String GT_NAME = "__GREATERTHAN";
  public static final String GEQ_NAME = "__GREATERTHANOREQUAL";
  public static final String LT_NAME = "__LESSTHAN";
  public static final String LEQ_NAME = "__LESSTHANOREQUAL";

  // can be called by user
  public static final String PREV_NAME = "prev";
  public static final String INV_NAME = "inv";
  public static final String DET_NAME = "det";
  public static final String LOG_DET_NAME = "logdet";
  public static final String TRACE_NAME = "trace";
  public static final String IS_EMPTY_NAME = "isEmptyString";
  public static final String MIN_NAME = "min";
  public static final String MAX_NAME = "max";
  public static final String ROUND_NAME = "round";
  public static final String DIAG_NAME = "diag";
  public static final String REPMAT_NAME = "repmat";
  public static final String SUBMAT_NAME = "submat";
  public static final String GET_ROW_VEC_NAME = "getrow";
  public static final String GET_ROWS_NAME = "getrows";
  public static final String GET_COL_VEC_NAME = "getcol";
  public static final String GET_COLS_NAME = "getcols";
  public static final String TRANSPOSE_NAME = "transpose";
  public static final String SIN_NAME = "sin";
  public static final String COS_NAME = "cos";
  public static final String TAN_NAME = "tan";
  public static final String ATAN2_NAME = "atan2";
  public static final String SUM_NAME = "sum";
  public static final String MAT_SUM_NAME = "matsum";
  public static final String ROW_SUM_NAME = "rowsum";
  public static final String COL_SUM_NAME = "colsum";
  public static final String SIZE_NAME = "size";
  public static final String VSTACK_NAME = "vstack";
  public static final String HSTACK_NAME = "hstack";
  public static final String EYE_NAME = "eye";
  public static final String ZEROS_NAME = "zeros";
  public static final String ONES_NAME = "ones";
  public static final String TOINT_NAME = "toInt";
  public static final String TOREAL_NAME = "toReal";
  public static final String TOSTRING_NAME = "toString";
  public static final String LOAD_REAL_MATRIX_NAME = "loadRealMatrix";
  public static final String ABS_NAME = "abs";
  public static final String EXP_NAME = "exp";
  public static final String LOG_NAME = "log";
  public static final String IOTA_NAME = "iota";
  public static final String NUMROWS_NAME = "numrows";
  public static final String NUMCOLS_NAME = "numcols";

  /**
   * Constant that always denotes Model.NULL.
   */
  public static final FixedFunction NULL;

  /**
   * Constant that denotes the natural number 0. The parser creates
   * NonRandomConstant objects as needed to represent numeric constants that it
   * actually encounters in a file, but some internal compilation code may need
   * to use this constant even if it doesn't occur in a file.
   */
  public static final FixedFunction ZERO;

  /**
   * Constant that denotes the timestep 0. The parser creates NonRandomConstant
   * objects as needed to represent timestep constants that it actually
   * encounters in a file, but some internal compilation code may need to use
   * this constant even if it doesn't occur in a file.
   */
  public static final FixedFunction EPOCH;

  /**
   * Constant that denotes E.
   */
  public static final FixedFunction E;

  /**
   * Constant that denotes PI.
   */
  public static final FixedFunction PI;

  /**
   * The LessThan relation on type Real (and its subtypes).
   */
  public static FixedFunction LT;

  /**
   * The LessThanOrEqual relation on type Real (and its subtypes).
   */
  public static FixedFunction LEQ;

  /**
   * The GreaterThan relation on type Real (and its subtypes)
   */
  public static FixedFunction GT;

  /**
   * The GreaterThanOrEqual relation on type Real (and its subtypes).
   */
  public static FixedFunction GEQ;

  /**
   * A function from integers to natural numbers that yields the non-negative
   * part of the given integer <code>x</code>: that is, <code>min(x, 0)</code>.
   */
  public static FixedFunction NON_NEG_PART;

  /**
   * The function on integers <code>x<code>, <code>y</code> that returns x + y.
   */
  public static FixedFunction PLUS;

  /**
   * The function on integers <code>x<code>, <code>y</code> that returns x - y.
   */
  public static FixedFunction MINUS;

  /**
   * The function on integers <code>x<code>, <code>y</code> that returns x * y.
   */
  public static FixedFunction MULT;

  /**
   * The function on integers <code>x<code>, <code>y</code> that returns x / y.
   */
  public static FixedFunction DIV;

  /**
   * The function on integers <code>x<code>, <code>y</code> that returns x % y.
   */
  public static FixedFunction MOD;

  /**
   * The function on timestep <code>x<code>, integer <code>y</code> that returns
   * x + y.
   */
  public static FixedFunction TSPLUS;

  /**
   * The function on timestep <code>x<code>, integer <code>y</code> that returns
   * x - y.
   */
  public static FixedFunction TSMINUS;

  /**
   * The function on timestep <code>x<code>, integer <code>y</code> that returns
   * x * y.
   */
  public static FixedFunction TSMULT;

  /**
   * The function on timestep <code>x<code>, integer <code>y</code> that returns
   * x / y.
   */
  public static FixedFunction TSDIV;

  /**
   * The function on timestep <code>x<code>, integer <code>y</code> that returns
   * x % y.
   */
  public static FixedFunction TSMOD;

  /**
   * The function on reals <code>x<code>, <code>y</code> that returns x + y.
   */
  public static FixedFunction RPLUS;

  /**
   * The function on reals <code>x<code>, <code>y</code> that returns x - y.
   */
  public static FixedFunction RMINUS;

  /**
   * The function on reals <code>x<code>, <code>y</code> that returns x * y.
   */
  public static FixedFunction RMULT;

  /**
   * The function on reals <code>x<code>, <code>y</code> that returns x ^ y.
   */
  public static FixedFunction POWER;

  /**
   * The function on reals <code>x<code>, <code>y</code> that returns x / y.
   */
  public static FixedFunction RDIV;

  /**
   * RealMatrix + RealMatrix returns RealMatrix
   */
  public static FixedFunction PLUS_MAT;

  /**
   * RealMatrix - RealMatrix returns RealMatrix
   */
  public static FixedFunction MINUS_MAT;

  /**
   * RealMatrix * RealMatrix returns RealMatrix (matrix multiplication)
   */
  public static FixedFunction TIMES_MAT;

  /**
   * RealMatrix * Real returns RealMatrix
   */
  public static FixedFunction TIMES_MAT_SCALAR;

  /**
   * Real * RealMatrix returns RealMatrix
   */
  public static FixedFunction TIMES_SCALAR_MAT;

  /**
   * inv(RealMatrix) returns RealMatrix (matrix inverse)
   */
  public static FixedFunction INV_MAT;

  /**
   * det(RealMatrix) returns Real (matrix determinant)
   */
  public static FixedFunction DET_MAT;

  /**
   * log det(RealMatrix) returns Real (logarithm of matrix determinant)
   */
  public static FixedFunction LOG_DET_MAT;

  /**
   * trace(RealMatrix) returns Real (matrix trace)
   */
  public static FixedFunction TRACE_MAT;

  /**
   * The predecessor function on timesteps. Given a positive timestep n, it
   * returns n-1. Given the timestep 0, it returns Model.NULL.
   */
  public static FixedFunction PREV;

  /**
   * A function on strings <code>x</code>, <code>y</code> that returns the
   * concatenation of <code>x</code> and <code>y</code>.
   */
  public static FixedFunction CONCAT;

  /**
   * RealMatrix[Integer] returns Real
   * (i-th element of matrix, indexing is column-wise)
   * i.e. A = [1,3;2,4]; Then A[0] = 1, A[1] = 2, A[2] = 3, A[4] = 4;
   */
  public static FixedFunction SUB_MAT;

  /**
   * RealMatrix[Integer][Integer] returns Real
   * (element in i-th row and j-th column of matrix)
   */
  public static FixedFunction SUB_MAT2;

  /**
   * RealArray[Integer] returns Real (i-th element of array)
   */
  public static FixedFunction SUB_REAL_ARRAY;

  /**
   * IntegerArray[Integer] returns Integer (i-th element of array)
   */
  public static FixedFunction SUB_INT_ARRAY;

  /**
   * a function on Set <code>x</code> returns the minimal value from the set
   */
  public static FixedFunction MIN;

  /**
   * a function on Set <code>x</code> returns the maximal value from the set
   */
  public static FixedFunction MAX;

  /**
   * a function on Real <code>x</code> returns the nearest integer to
   * <code>x</code>
   */
  public static FixedFunction ROUND;

  /**
   * Diagonal(RealMatrix) returns RealMatrix
   */
  public static FixedFunction DIAG_REAL_MAT;

  /**
   * Repmat(RealMatrix) returns RealMatrix
   */
  public static FixedFunction REPMAT_REAL;

  /**
   * Submat(RealMatrix) returns RealMatrix
   */
  public static FixedFunction SUBMAT_REAL;

  /**
   * getrow(RealMatrix, r) returns the rth row vector of RealMatrix
   */
  public static FixedFunction GETROW_VEC_MAT;

  /**
   * getcol(RealMatrix, c) returns the cth column vector of RealMatrix
   */
  public static FixedFunction GETCOL_VEC_MAT;

  /**
   * getrows(RealMatrix, r1, r2)
   * returns the submat of row vectors in [r1,r2] in RealMatrix
   */
  public static FixedFunction GETROWS_MAT;

  /**
   * getcols(RealMatrix, c1, c2)
   * returns the submat of column vectors in [c1,c2] in RealMatrix
   */
  public static FixedFunction GETCOLS_MAT;

  /**
   * transpose(RealMatrix) returns RealMatrix
   */
  public static FixedFunction TRANSPOSE_REAL_MAT;

  /**
   * transpose(IntegerMatrix) returns IntegerMatrix
   */
  public static FixedFunction TRANSPOSE_INT_MAT;

  /**
   * A function that takes a string and returns true if the string is empty.
   */
  public static FixedFunction IS_EMPTY_STRING;

  /**
   * Take scalar <code>x</code> (in radians) and return <code>sin(x)</code>.
   */
  public static FixedFunction SIN;

  /**
   * Take scalar <code>x</code> (in radians) and return <code>cos(x)</code>.
   */
  public static FixedFunction COS;

  /**
   * Take scalar <code>x</code> (in radians) and return <code>tan(x)</code>.
   */
  public static FixedFunction TAN;

  /**
   * Take scalars <code>x</code> and <code>y</code> and return
   * <code>atan2(y, x)</code>.
   */
  public static FixedFunction ATAN2;

  /**
   * Take RealMatrix x and return RealMatrix y where elements are the sum of
   * columns of x.
   */
  public static FixedFunction COL_SUM;

  /**
   * Take RealMatrix x and return RealMatrix y where elements are the sum of
   * rows of x.
   */
  public static FixedFunction ROW_SUM;

  /**
   * Take RealMatrix x and return sum of all the elements in x.
   */
  public static FixedFunction MAT_SUM;

  /**
   * Take a Set x of Real values, and return the sum of its elements.
   */
  public static FixedFunction SET_SUM;

  /**
   * Take a Set x of values (any type, including user declared type), and return
   * the number of elements in the Set.
   */
  public static FixedFunction SET_SIZE;

  /**
   * Special case for VSTACK when arguments are all matrices
   */
  private static FunctionInterp VSTACK_MATRIX_INTERP;

  /**
   * Special case for VSTACK when arguments are all scalars
   */
  private static FunctionInterp VSTACK_SCALAR_INTERP;

  /**
   * Take RealMatrices [x; y; ...] and return RealMatrix z which is the
   * vertical concatenation
   * Arguments could be either all matrices or all scalars
   */
  public static TemplateFunction VSTACK;

  /**
   * Special case for HSTACK when arguments are all matrices
   */
  private static FunctionInterp HSTACK_MATRIX_INTERP;

  /**
   * Special case for HSTACK when arguments are all scalars
   */
  private static FunctionInterp HSTACK_SCALAR_INTERP;

  /**
   * Take RealMatrices [x, y, ...] and return RealMatrix z which is the
   * horizontal concatenation
   * Arguments could be either all matrices or all scalars
   */
  public static TemplateFunction HSTACK;

  /**
   * Return an identity matrix.
   * EYE_k will take k integers as input arguments
   */
  public static FixedFunction EYE1;
  public static FixedFunction EYE2;

  /**
   * Return a matrix of zeros.
   * ZEROS_k will take k integers as input arguments
   */
  public static FixedFunction ZEROS1;
  public static FixedFunction ZEROS2;

  /**
   * Return a matrix of ones.
   * ONES_k will take k integers as input arguments
   */
  public static FixedFunction ONES1;
  public static FixedFunction ONES2;

  /**
   * The function takes an arbitrary Object,
   * and converts it to an String
   */
  public static FixedFunction TO_STRING;

  /**
   * The function takes an Integer, an Real or a MatrixLib with single element,
   * and converts it to an Integer
   */
  public static TemplateFunction TO_INT;

  /**
   * from number to integer
   */
  private static FunctionInterp NUMTOINT_INTERP;

  /**
   * from boolean to integer
   */
  private static FunctionInterp BOOLTOINT_INTERP;

  /**
   * from timestep to integer
   */
  private static FunctionInterp TIMESTEPTOINT_INTERP;

  /**
   * from string to integer
   */
  private static FunctionInterp STRINGTOINT_INTERP;

  /**
   * from first element of matrix to integer
   */
  private static FunctionInterp MATRIXTOINT_INTERP;

  /**
   * The function takes a Real or a MatrixLib with single element,
   * and converts it to a Real
   */
  public static FixedFunction TO_REAL;

  /**
   * Return the absolute value of a Real value.
   */
  public static FixedFunction ABS;

  /**
   * Return the absolute value of a Integer value.
   */
  public static FixedFunction ABS_INT;

  /**
   * Return the absolute value of every element of a Real matrix.
   */
  public static FixedFunction ABS_MAT;

  /**
   * Return the exponential value of a Real value.
   */
  public static FixedFunction EXP;

  /**
   * Return the exponential value of a Integer value.
   */
  public static FixedFunction EXP_INT;

  /**
   * Return the exponential value of every element in the matrix.
   */
  public static FixedFunction EXP_MAT;

  /**
   * Return the natural logarithm value of a Real value.
   */
  public static FixedFunction LOG;

  /**
   * Return the natural logarithm value of a Integer value.
   */
  public static FixedFunction LOG_INT;

  /**
   * Return the natural logarithm value of every element in the matrix.
   */
  public static FixedFunction LOG_MAT;

  /**
   * Return the element from a singleton set.
   */
  public static FixedFunction IOTA;

  /**
   * Load RealMatrix from space-separeted text file.
   */
  public static FixedFunction LOAD_REAL_MATRIX;

  /**
   * Load i-th row of RealMatrix from space-separeted text file.
   */
  public static FixedFunction LOAD_REAL_MATRIX_ROW;

  /**
   * Load i-th to j-th rows (inclusive) RealMatrix from space-separeted text
   * file.
   */
  public static FixedFunction LOAD_REAL_MATRIX_ROWS;

  /**
   * Load submatrix (inclusive) RealMatrix from space-separeted text
   * file.
   */
  public static FixedFunction LOAD_REAL_MATRIX_SUB;

  /**
   * Number of rows for a given real matrix.
   */
  public static FixedFunction NUM_ROWS;

  /**
   * Number of columns for a given real matrix.
   */
  public static FixedFunction NUM_COLS;

  private BuiltInFunctions() {
    // prevent instantiation
  }

  /**
   * Returns the built-in function (or constant) with the given signature.
   * Returns null if there is no such built-in function, or if the given name is
   * a numeric, character, or string literal that is only created as needed by
   * the parser.
   */
  public static FixedFunction getFunction(FunctionSignature sig) {

    // TODO change to another hashmap from signature to function
    List funcsWithName = (List) functions.get(sig.getName());
    if (funcsWithName != null) {
      for (Iterator iter = funcsWithName.iterator(); iter.hasNext();) {
        FixedFunction f = (FixedFunction) iter.next();
        if (Arrays.equals(sig.getArgTypes(), f.getArgTypes())) {
          return f;
        }
      }
    }

    // find template functions compatible with sig
    TemplateFunction tempfun = templateFunctions.get(sig.getName());
    if (tempfun != null) {
      FixedFunction f = tempfun.getConcreteFunction(sig.getArgTypes());
      if (f != null)
        return f;
    }

    return null;
  }

  /**
   * Returns the built-in constant symbol with the given name, which has the
   * given return type and denotes the given value. Creates the constant symbol
   * automatically if it hasn't been created yet.
   */
  public static FixedFunction getLiteral(String name, Type type, Object value) {
    FixedFunction f = getFunction(new FunctionSignature(name));
    if (f == null) {
      List params = Collections.singletonList(value);
      f = new FixedFunction(name, Collections.EMPTY_LIST, type,
          new ConstantInterp(params));
      addFunction(f);
    }
    return f;
  }

  /**
   * Returns the built-in functions (and constants) with the given name.
   * 
   * @return unmodifiable List of Function
   */
  public static List getFuncsWithName(String name) {
    List funcsWithName = (List) functions.get(name);
    return (funcsWithName == null) ? Collections.EMPTY_LIST : Collections
        .unmodifiableList(funcsWithName);
  }

  private static void addFunction(Function f) {
    List funcsWithName = (List) functions.get(f.getName());
    if (funcsWithName != null) {
      for (Iterator iter = funcsWithName.iterator(); iter.hasNext();) {
        Function g = (Function) iter.next();
        if (Arrays.equals(g.getArgTypes(), f.getArgTypes())) {
          System.err.println("Warning: overwriting existing " + "function "
              + g.getSig());
          iter.remove();
        }
      }
    } else {
      funcsWithName = new ArrayList();
      functions.put(f.getName(), funcsWithName);
    }
    funcsWithName.add(f);
  }

  static Map functions = new HashMap(); // from String to List of Function

  private static void addTemplate(TemplateFunction t) {
    templateFunctions.put(t.getName(), t);
  }

  static Map<String, TemplateFunction> templateFunctions = new HashMap<String, TemplateFunction>();

  static {
    // Add non-random constants
    NULL = getLiteral("null", BuiltInTypes.NULL, Model.NULL);
    ZERO = getLiteral("0", BuiltInTypes.INTEGER, new Integer(0));
    EPOCH = getLiteral("@0", BuiltInTypes.TIMESTEP, Timestep.at(0));
    E = getLiteral("e", BuiltInTypes.REAL, new Double(Math.E));
    PI = getLiteral("pi", BuiltInTypes.REAL, new Double(Math.PI));

    // Add non-random functions from (real x real) to Boolean
    List<Type> argTypes = new ArrayList<Type>();
    argTypes.add(BuiltInTypes.REAL);
    argTypes.add(BuiltInTypes.REAL);
    Type retType = BuiltInTypes.BOOLEAN;

    FunctionInterp ltInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double arg1 = ((Number) args.get(0)).doubleValue();
        double arg2 = ((Number) args.get(1)).doubleValue();
        return Boolean.valueOf(arg1 < arg2);
      }
    };
    LT = new FixedFunction(LT_NAME, argTypes, retType, ltInterp);
    addFunction(LT);

    FunctionInterp leqInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double arg1 = ((Number) args.get(0)).doubleValue();
        double arg2 = ((Number) args.get(1)).doubleValue();
        return Boolean.valueOf(arg1 <= arg2);
      }
    };
    LEQ = new FixedFunction(LEQ_NAME, argTypes, retType, leqInterp);
    addFunction(LEQ);

    FunctionInterp gtInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double arg1 = ((Number) args.get(0)).doubleValue();
        double arg2 = ((Number) args.get(1)).doubleValue();
        return Boolean.valueOf(arg1 > arg2);
      }
    };
    GT = new FixedFunction(GT_NAME, argTypes, retType, gtInterp);
    addFunction(GT);

    FunctionInterp geqInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double arg1 = ((Number) args.get(0)).doubleValue();
        double arg2 = ((Number) args.get(1)).doubleValue();
        return Boolean.valueOf(arg1 >= arg2);
      }
    };
    GEQ = new FixedFunction(GEQ_NAME, argTypes, retType, geqInterp);
    addFunction(GEQ);

    // Add non-random functions from natural number to natural number
    argTypes.clear();
    argTypes.add(BuiltInTypes.NATURAL_NUM);
    retType = BuiltInTypes.NATURAL_NUM;

    // Add non-random functions from integer to natural number
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.NATURAL_NUM;

    FunctionInterp nonNegPartInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg = (Integer) args.get(0);
        int n = arg.intValue();
        return new Integer((n < 0) ? 0 : n);
      }
    };
    NON_NEG_PART = new FixedFunction("NonNegPart", argTypes, retType,
        nonNegPartInterp);
    addFunction(NON_NEG_PART);

    // Add non-random functions from (integer x integer) to integer
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.INTEGER;

    FunctionInterp plusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg1 = (Integer) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        return new Integer(arg1.intValue() + arg2.intValue());
      }
    };
    PLUS = new FixedFunction(PLUS_NAME, argTypes, retType, plusInterp);
    addFunction(PLUS);

    // Multiply non-random functions from (integer x integer) to integer
    FunctionInterp multInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg1 = (Integer) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        return new Integer(arg1.intValue() * arg2.intValue());
      }
    };
    MULT = new FixedFunction(MULT_NAME, argTypes, retType, multInterp);
    addFunction(MULT);

    FunctionInterp minusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg1 = (Integer) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        return new Integer(arg1.intValue() - arg2.intValue());
      }
    };
    MINUS = new FixedFunction(MINUS_NAME, argTypes, retType, minusInterp);
    addFunction(MINUS);

    // Divide non-random functions from (integer x integer) to integer
    FunctionInterp divInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg1 = (Integer) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        return new Integer(arg1.intValue() / arg2.intValue());
      }
    };
    DIV = new FixedFunction(DIV_NAME, argTypes, retType, divInterp);
    addFunction(DIV);

    // Mod non-random functions from (integer x integer) to integer
    FunctionInterp modInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer arg1 = (Integer) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        return new Integer(arg1.intValue() % arg2.intValue());
      }
    };
    MOD = new FixedFunction(MOD_NAME, argTypes, retType, modInterp);
    addFunction(MOD);

    // Add non-random functions from (real x real) to real
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;

    FunctionInterp rplusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Number arg1 = (Number) args.get(0);
        Number arg2 = (Number) args.get(1);
        return new Double(arg1.doubleValue() + arg2.doubleValue());
      }
    };
    RPLUS = new FixedFunction(PLUS_NAME, argTypes, retType, rplusInterp);
    addFunction(RPLUS);

    FunctionInterp rminusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Number arg1 = (Number) args.get(0);
        Number arg2 = (Number) args.get(1);
        return new Double(arg1.doubleValue() - arg2.doubleValue());
      }
    };
    RMINUS = new FixedFunction(MINUS_NAME, argTypes, retType, rminusInterp);
    addFunction(RMINUS);

    FunctionInterp rmultInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Number arg1 = (Number) args.get(0);
        Number arg2 = (Number) args.get(1);
        return new Double(arg1.doubleValue() * arg2.doubleValue());
      }
    };
    RMULT = new FixedFunction(MULT_NAME, argTypes, retType, rmultInterp);
    addFunction(RMULT);

    FunctionInterp rdivInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Number arg1 = (Number) args.get(0);
        Number arg2 = (Number) args.get(1);
        return new Double(arg1.doubleValue() / arg2.doubleValue());
      }
    };
    RDIV = new FixedFunction(DIV_NAME, argTypes, retType, rdivInterp);
    addFunction(RDIV);

    FunctionInterp powerInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Number arg1 = (Number) args.get(0);
        Number arg2 = (Number) args.get(1);
        return new Double(Math.pow(arg1.doubleValue(), arg2.doubleValue()));
      }
    };
    POWER = new FixedFunction(POWER_NAME, argTypes, retType, powerInterp);
    addFunction(POWER);

    // Add non-random functions from timestep to timestep
    argTypes.clear();
    argTypes.add(BuiltInTypes.TIMESTEP);
    retType = BuiltInTypes.TIMESTEP;

    FunctionInterp prevInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg = (Timestep) args.get(0);
        if (arg.getValue() <= 0) {
          return Model.NULL;
        }
        return Timestep.at(arg.getValue() - 1);
      }
    };
    PREV = new FixedFunction(PREV_NAME, argTypes, retType, prevInterp);
    addFunction(PREV);

    // Add non-random functions from (TimeStep x Integer) to TimeStep
    argTypes.clear();
    argTypes.add(BuiltInTypes.TIMESTEP);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.TIMESTEP;

    FunctionInterp tsplusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg1 = (Timestep) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        if (arg1.getValue() + arg2.intValue() < 0)
          return Model.NULL;
        return Timestep.at(arg1.getValue() + arg2.intValue());
      }
    };
    TSPLUS = new FixedFunction(PLUS_NAME, argTypes, retType, tsplusInterp);
    addFunction(TSPLUS);

    FunctionInterp tsminusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg1 = (Timestep) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        if (arg1.getValue() < arg2.intValue())
          return Model.NULL;
        return Timestep.at(arg1.getValue() - arg2.intValue());
      }
    };
    TSMINUS = new FixedFunction(MINUS_NAME, argTypes, retType, tsminusInterp);
    addFunction(TSMINUS);

    FunctionInterp tsmultInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg1 = (Timestep) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        if (arg2.intValue() < 0)
          return Model.NULL;
        return Timestep.at(arg1.getValue() * arg2.intValue());
      }
    };
    TSMULT = new FixedFunction(MULT_NAME, argTypes, retType, tsmultInterp);
    addFunction(TSMULT);

    FunctionInterp tsdivInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg1 = (Timestep) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        if (arg2.intValue() <= 0)
          return Model.NULL;
        return Timestep.at(arg1.getValue() / arg2.intValue());
      }
    };
    TSDIV = new FixedFunction(DIV_NAME, argTypes, retType, tsdivInterp);
    addFunction(TSDIV);

    FunctionInterp tsmodInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Timestep arg1 = (Timestep) args.get(0);
        Integer arg2 = (Integer) args.get(1);
        if (arg2.intValue() <= 0)
          return Model.NULL;
        return Timestep.at(arg1.getValue() % arg2.intValue());
      }
    };
    TSMOD = new FixedFunction(MOD_NAME, argTypes, retType, tsmodInterp);
    addFunction(TSMOD);

    // Add non-random functions from (string x string) to string
    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    argTypes.add(BuiltInTypes.STRING);
    retType = BuiltInTypes.STRING;

    FunctionInterp concatInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        String arg1 = (String) args.get(0);
        String arg2 = (String) args.get(1);
        return arg1.concat(arg2);
      }
    };
    CONCAT = new FixedFunction(PLUS_NAME, argTypes, retType, concatInterp);
    addFunction(CONCAT);

    // Add non-random functions from string to Boolean
    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    retType = BuiltInTypes.BOOLEAN;

    FunctionInterp isEmptyStringInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return Boolean.valueOf(((String) args.get(0)).length() == 0);
      }
    };
    IS_EMPTY_STRING = new FixedFunction(IS_EMPTY_NAME, argTypes, retType,
        isEmptyStringInterp);
    addFunction(IS_EMPTY_STRING);

    // Add non-random functions from (RealMatrix x int) to Real
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL;

    // Return the i-th element of the matrix,
    FunctionInterp subMatInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat = (MatrixLib) args.get(0);
        int i = (Integer) args.get(1);
        int n_row = mat.numRows();
        int x = i % n_row, y = i / n_row;
        return mat.elementAt(x, y);
      }
    };
    SUB_MAT = new FixedFunction(ARRAY_MAT_ELEMENT_NAME, argTypes, retType,
        subMatInterp);
    addFunction(SUB_MAT);

    // Add non-random functions from (RealMatrix x int x int) to Real
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL;

    // Return the element in i-th row and j-th column of the matrix,
    FunctionInterp subMat2Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat = (MatrixLib) args.get(0);
        int i = (Integer) args.get(1);
        int j = (Integer) args.get(2);
        return mat.elementAt(i, j);
      }
    };
    SUB_MAT2 = new FixedFunction(ARRAY_MAT_ELEMENT_NAME, argTypes, retType,
        subMat2Interp);
    addFunction(SUB_MAT2);

    // Array subscription (aka indexing)
    FunctionInterp subDoubleInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        ArrayList<?> array = (ArrayList<?>) args.get(0);
        int i = (Integer) args.get(1);
        return (Double) array.get(i);
      }
    };

    // Array indexing for Real arrays:
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_ARRAY);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL;
    SUB_REAL_ARRAY = new FixedFunction(ARRAY_MAT_ELEMENT_NAME, argTypes,
        retType, subDoubleInterp);
    addFunction(SUB_REAL_ARRAY);

    // Array subscription (aka indexing)
    FunctionInterp subIntInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        ArrayList<?> array = (ArrayList<?>) args.get(0);
        int i = (Integer) args.get(1);
        return (Integer) array.get(i);
      }
    };

    // Array indexing for Integer arrays:
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER_ARRAY);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.INTEGER;
    SUB_INT_ARRAY = new FixedFunction(ARRAY_MAT_ELEMENT_NAME, argTypes,
        retType, subIntInterp);
    addFunction(SUB_INT_ARRAY);

    // Add non-random functions from (RealMatrix x RealMatrix) to RealMatrix
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;

    // matrix plus
    FunctionInterp matPlusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        MatrixLib mat2 = (MatrixLib) args.get(1);
        return mat1.plus(mat2);
      }
    };
    PLUS_MAT = new FixedFunction(PLUS_NAME, argTypes, retType, matPlusInterp);
    addFunction(PLUS_MAT);

    // matrix minus
    FunctionInterp matMinusInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        MatrixLib mat2 = (MatrixLib) args.get(1);
        return mat1.minus(mat2);
      }
    };
    MINUS_MAT = new FixedFunction(MINUS_NAME, argTypes, retType, matMinusInterp);
    addFunction(MINUS_MAT);

    // matrix multiplication
    FunctionInterp matTimesInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        MatrixLib mat2 = (MatrixLib) args.get(1);
        return mat1.timesMat(mat2);
      }
    };
    TIMES_MAT = new FixedFunction(MULT_NAME, argTypes, retType, matTimesInterp);
    addFunction(TIMES_MAT);

    // Add non-random functions from (RealMatrix x Real) to RealMatrix
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL_MATRIX;

    // matrix scalar multiplication
    FunctionInterp matScalarTimesInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double val = (Double) args.get(1);
        MatrixLib mat = (MatrixLib) args.get(0);
        return mat.timesScale(val);
      }
    };
    TIMES_MAT_SCALAR = new FixedFunction(MULT_NAME, argTypes, retType,
        matScalarTimesInterp);
    addFunction(TIMES_MAT_SCALAR);

    // Add non-random functions from (Real x RealMatrix) to RealMatrix
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;

    // scalar matrix multiplication
    FunctionInterp scalarMatTimesInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double val = ((Number) args.get(0)).doubleValue();
        MatrixLib mat2 = (MatrixLib) args.get(1);
        return mat2.timesScale(val);
      }
    };
    TIMES_SCALAR_MAT = new FixedFunction(MULT_NAME, argTypes, retType,
        scalarMatTimesInterp);
    addFunction(TIMES_SCALAR_MAT);

    // Add non-random functions from RealMatrix to RealMatrix
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;

    // matrix inverse
    FunctionInterp matInverseInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        return mat1.inverse();
      }
    };
    INV_MAT = new FixedFunction(INV_NAME, argTypes, retType, matInverseInterp);
    addFunction(INV_MAT);

    // Add non-random functions from RealMatrix to Real
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL;

    // matrix determinant
    FunctionInterp matDeterminantInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        return mat1.det();
      }
    };
    DET_MAT = new FixedFunction(DET_NAME, argTypes, retType,
        matDeterminantInterp);
    addFunction(DET_MAT);

    // log of matrix determinant
    FunctionInterp logmatDeterminantInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        return mat1.logDet();
      }
    };
    LOG_DET_MAT = new FixedFunction(LOG_DET_NAME, argTypes, retType,
        logmatDeterminantInterp);
    addFunction(LOG_DET_MAT);

    // matrix trace
    FunctionInterp matTraceInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib mat1 = (MatrixLib) args.get(0);
        return mat1.trace();
      }
    };
    TRACE_MAT = new FixedFunction(TRACE_NAME, argTypes, retType, matTraceInterp);
    addFunction(TRACE_MAT);

    // Min of a set.
    FunctionInterp minInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return Collections.min((Collection<? extends Comparable>) args.get(0));
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.SET);
    retType = BuiltInTypes.ANY;
    MIN = new FixedFunction(MIN_NAME, argTypes, retType, minInterp);
    addFunction(MIN);

    // Max of a set.
    FunctionInterp maxInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return Collections.max((Collection<? extends Comparable>) args.get(0));
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.SET);
    retType = BuiltInTypes.ANY;
    MAX = new FixedFunction(MAX_NAME, argTypes, retType, maxInterp);
    addFunction(MAX);

    // Add non-random functions from Real to Integer
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.INTEGER;

    FunctionInterp roundInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Double num = (Double) args.get(0);
        return new Integer((int) Math.round(num));
      }
    };

    ROUND = new FixedFunction(ROUND_NAME, argTypes, retType, roundInterp);
    addFunction(ROUND);

    // Diag function for Real matrices
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;

    FunctionInterp diagInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        if (matrix.numCols() == 1) {
          MatrixLib diagMatrix = MatrixFactory.eye(matrix.numRows());
          for (int i = 0; i < matrix.numRows(); i++) {
            diagMatrix.setElement(i, i, matrix.elementAt(i, 0));
          }
          return diagMatrix;
        } else {
          throw new IllegalArgumentException("diag expected " + "column vector");
        }
      }
    };
    DIAG_REAL_MAT = new FixedFunction(DIAG_NAME, argTypes, retType, diagInterp);
    addFunction(DIAG_REAL_MAT);

    // Repmat function for Real matrices
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;

    FunctionInterp repMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer rowTimes = (Integer) args.get(1);
        Integer colTimes = (Integer) args.get(2);
        return matrix.repmat(rowTimes, colTimes);
      }
    };
    REPMAT_REAL = new FixedFunction(REPMAT_NAME, argTypes, retType, repMatReal);
    addFunction(REPMAT_REAL);

    // Get-Row-Vector function for Real matrices
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    argTypes.add(BuiltInTypes.INTEGER);
    FunctionInterp getRowVecMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer r = (Integer) args.get(1);
        return matrix.sliceRow(r);
      }
    };
    GETROW_VEC_MAT = new FixedFunction(GET_ROW_VEC_NAME, argTypes, retType,
        getRowVecMatReal);
    addFunction(GETROW_VEC_MAT);

    // Get-Col-Vector function for Real matrices
    FunctionInterp getColVecMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer c = (Integer) args.get(1);
        return matrix.sliceCol(c);
      }
    };
    GETCOL_VEC_MAT = new FixedFunction(GET_COL_VEC_NAME, argTypes, retType,
        getColVecMatReal);
    addFunction(GETCOL_VEC_MAT);

    // Get-Rows-SubMatrix function for Real matrices
    argTypes.add(BuiltInTypes.INTEGER);
    FunctionInterp getRowsSubMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer r1 = (Integer) args.get(1);
        Integer r2 = (Integer) args.get(2);
        return matrix.sliceRows(r1, r2); // inclusive
      }
    };
    GETROWS_MAT = new FixedFunction(GET_ROWS_NAME, argTypes, retType,
        getRowsSubMatReal);
    addFunction(GETROWS_MAT);

    // Get-Cols-SubMatrix function for Real matrices
    FunctionInterp getColsSubMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer c1 = (Integer) args.get(1);
        Integer c2 = (Integer) args.get(2);
        return matrix.sliceCols(c1, c2); // inclusive
      }
    };
    GETCOLS_MAT = new FixedFunction(GET_COLS_NAME, argTypes, retType,
        getColsSubMatReal);
    addFunction(GETCOLS_MAT);

    // Sub-Matrix function for Real matrices
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    FunctionInterp subMatReal = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        Integer x1 = (Integer) args.get(1);
        Integer y1 = (Integer) args.get(2);
        Integer x2 = (Integer) args.get(3);
        Integer y2 = (Integer) args.get(4);
        return matrix.subMat(x1, y1, x2, y2); // inclusive for both rows & cols
      }
    };
    SUBMAT_REAL = new FixedFunction(SUBMAT_NAME, argTypes, retType, subMatReal);
    addFunction(SUBMAT_REAL);

    // Transpose function for Real matrices
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;

    FunctionInterp transposeInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        return matrix.transpose();
      }
    };

    TRANSPOSE_REAL_MAT = new FixedFunction(TRANSPOSE_NAME, argTypes, retType,
        transposeInterp);
    addFunction(TRANSPOSE_REAL_MAT);

    // Transpose function for Integer matrices (uses the same FunctionInterp
    // above)
    // Does not work yet ("java.util.ArrayList cannot be cast to
    // blog.common.numerical.MatrixLib")
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER_MATRIX);
    retType = BuiltInTypes.INTEGER_MATRIX;
    TRANSPOSE_INT_MAT = new FixedFunction(TRANSPOSE_NAME, argTypes, retType,
        transposeInterp);
    addFunction(TRANSPOSE_INT_MAT);

    // Trigonometric functions on scalars:
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;

    FunctionInterp sinInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Double radians = (Double) args.get(0);
        return Math.sin(radians);
      }
    };
    SIN = new FixedFunction(SIN_NAME, argTypes, retType, sinInterp);
    addFunction(SIN);

    FunctionInterp cosInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Double radians = (Double) args.get(0);
        return Math.cos(radians);
      }
    };
    COS = new FixedFunction(COS_NAME, argTypes, retType, cosInterp);
    addFunction(COS);

    FunctionInterp tanInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Double radians = (Double) args.get(0);
        return Math.tan(radians);
      }
    };
    TAN = new FixedFunction(TAN_NAME, argTypes, retType, tanInterp);
    addFunction(TAN);

    FunctionInterp atan2Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Double y = (Double) args.get(0);
        Double x = (Double) args.get(1);
        return Math.atan2(y, x);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;
    ATAN2 = new FixedFunction(ATAN2_NAME, argTypes, retType, atan2Interp);
    addFunction(ATAN2);

    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;
    FunctionInterp colSumInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        return matrix.columnSum();
      }
    };
    COL_SUM = new FixedFunction(COL_SUM_NAME, argTypes, retType, colSumInterp);
    addFunction(COL_SUM);

    FunctionInterp rowSumInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        return matrix.rowSum();
      }
    };
    ROW_SUM = new FixedFunction(ROW_SUM_NAME, argTypes, retType, rowSumInterp);
    addFunction(ROW_SUM);

    FunctionInterp matSumInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib matrix = (MatrixLib) args.get(0);
        return matrix.matSum();
      }
    };
    MAT_SUM = new FixedFunction(MAT_SUM_NAME, argTypes, retType, matSumInterp);
    addFunction(MAT_SUM);

    FunctionInterp setSumInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Collection set = (Collection) args.get(0);
        double sum = 0;
        for (Object obj : set) {
          sum += ((Number) obj).doubleValue();
        }
        return sum;
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.SET);
    retType = BuiltInTypes.REAL;
    SET_SUM = new FixedFunction(SUM_NAME, argTypes, retType, setSumInterp);
    addFunction(SET_SUM);

    /**
     * defining size(set) function
     */
    FunctionInterp setSizeInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Collection<?> set = (Collection<?>) args.get(0);
        return set.size();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.SET);
    retType = BuiltInTypes.INTEGER;
    SET_SIZE = new FixedFunction(SIZE_NAME, argTypes, retType, setSizeInterp);
    addFunction(SET_SIZE);

    /**
     * return the element in a singleton set
     */
    FunctionInterp iotaInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Collection<?> set = (Collection<?>) args.get(0);
        if (set.size() != 1) {
          throw new IllegalArgumentException("iota expects a singleton set");
        }
        return set.iterator().next();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.SET);
    retType = BuiltInTypes.ANY;
    IOTA = new FixedFunction(IOTA_NAME, argTypes, retType, iotaInterp);
    addFunction(IOTA);

    HSTACK_SCALAR_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        int m = args.size();
        double[][] val = new double[1][m];
        for (int i = 0; i < m; ++i)
          val[0][i] = (Double) args.get(i);
        return MatrixFactory.fromArray(val);
      }
    };
    HSTACK_MATRIX_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib ret = (MatrixLib) args.get(0);
        for (int i = 1; i < args.size(); ++i)
          ret = MatrixFactory.hstack(ret, (MatrixLib) args.get(i));
        return ret;
      }
    };
    HSTACK = new TemplateFunction(HSTACK_NAME) {

      @Override
      public FixedFunction getConcreteFunction(Type[] argTypes) {
        if (argTypes == null || argTypes.length < 1)
          return null;

        /*
         * Currently only support convert elements to Real Matrix!!!
         * TODO: to support other types in the future
         */
        List<Type> args = new ArrayList<Type>();
        boolean all_real = true, all_matrix = true;
        for (Type ty : argTypes) {
          if (ty.isSubtypeOf(BuiltInTypes.REAL)) {
            all_matrix = false; // has a real number, so not all are matrices
            args.add(BuiltInTypes.REAL);
            continue;
          }
          if (ty.isSubtypeOf(BuiltInTypes.REAL_MATRIX)) {
            all_real = false; // has a matrix, so not all are real numbers
            args.add(BuiltInTypes.REAL_MATRIX);
            continue;
          }
          return null;
        }

        FunctionInterp HStackInterp = null;

        if (all_real) { // only scalars
          HStackInterp = HSTACK_SCALAR_INTERP;
        } else if (all_matrix) { // only matrices
          HStackInterp = HSTACK_MATRIX_INTERP;
        } else {
          // Currently not support mixture of types
          return null;
        }

        FixedFunction retFunc = new FixedFunction(HSTACK_NAME, args,
            BuiltInTypes.REAL_MATRIX, HStackInterp);
        return retFunc;
      }
    };
    addTemplate(HSTACK);

    VSTACK_SCALAR_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        int n = args.size();
        double[][] val = new double[n][1];
        for (int i = 0; i < n; ++i)
          val[i][0] = (Double) args.get(i);
        return MatrixFactory.fromArray(val);
      }
    };
    VSTACK_MATRIX_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        MatrixLib ret = (MatrixLib) args.get(0);
        for (int i = 1; i < args.size(); ++i)
          ret = MatrixFactory.vstack(ret, (MatrixLib) args.get(i));
        return ret;
      }
    };
    VSTACK = new TemplateFunction(VSTACK_NAME) {

      @Override
      public FixedFunction getConcreteFunction(Type[] argTypes) {
        if (argTypes == null || argTypes.length < 1)
          return null;

        /*
         * Currently only support convert elements to Real Matrix!!!
         * TODO: to support other types in the future
         */
        List<Type> args = new ArrayList<Type>();
        boolean all_real = true, all_matrix = true;
        for (Type ty : argTypes) {
          if (ty.isSubtypeOf(BuiltInTypes.REAL)) {
            all_matrix = false;// has a real number, so not all are matrices
            args.add(BuiltInTypes.REAL);
            continue;
          }
          if (ty.isSubtypeOf(BuiltInTypes.REAL_MATRIX)) {
            all_real = false; // has a matrix, so not all are real numbers
            args.add(BuiltInTypes.REAL_MATRIX);
            continue;
          }
          return null;
        }

        FunctionInterp VStackInterp = null;

        if (all_real) { // only scalars
          VStackInterp = VSTACK_SCALAR_INTERP;
        } else if (all_matrix) { // only matrices
          VStackInterp = VSTACK_MATRIX_INTERP;
        } else {
          // Currently we do not support mixture of types
          return null;
        }

        FixedFunction retFunc = new FixedFunction(VSTACK_NAME, args,
            BuiltInTypes.REAL_MATRIX, VStackInterp);
        return retFunc;
      }
    };
    addTemplate(VSTACK);

    FunctionInterp eye1Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer size = (Integer) args.get(0);
        return MatrixFactory.eye(size);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    EYE1 = new FixedFunction(EYE_NAME, argTypes, retType, eye1Interp);
    addFunction(EYE1);

    FunctionInterp eye2Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer row = (Integer) args.get(0);
        Integer col = (Integer) args.get(1);
        return MatrixFactory.eye(row, col);
      }
    };
    argTypes.add(BuiltInTypes.INTEGER);
    EYE2 = new FixedFunction(EYE_NAME, argTypes, retType, eye2Interp);
    addFunction(EYE2);

    FunctionInterp zeros1Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer size = (Integer) args.get(0);
        return MatrixFactory.zeros(size);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    ZEROS1 = new FixedFunction(ZEROS_NAME, argTypes, retType, zeros1Interp);
    addFunction(ZEROS1);

    FunctionInterp zeros2Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer rows = (Integer) args.get(0);
        Integer cols = (Integer) args.get(1);
        return MatrixFactory.zeros(rows, cols);
      }
    };
    argTypes.add(BuiltInTypes.INTEGER);
    ZEROS2 = new FixedFunction(ZEROS_NAME, argTypes, retType, zeros2Interp);
    addFunction(ZEROS2);

    FunctionInterp ones1Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer size = (Integer) args.get(0);
        return MatrixFactory.ones(size);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    ONES1 = new FixedFunction(ONES_NAME, argTypes, retType, ones1Interp);
    addFunction(ONES1);

    FunctionInterp ones2Interp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Integer rows = (Integer) args.get(0);
        Integer cols = (Integer) args.get(1);
        return MatrixFactory.ones(rows, cols);
      }
    };
    argTypes.add(BuiltInTypes.INTEGER);
    ONES2 = new FixedFunction(ONES_NAME, argTypes, retType, ones2Interp);
    addFunction(ONES2);

    FunctionInterp toStringInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return obj.toString();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.BUILT_IN);
    retType = BuiltInTypes.STRING;
    TO_STRING = new FixedFunction(TOSTRING_NAME, argTypes, retType,
        toStringInterp);
    addFunction(TO_STRING);

    NUMTOINT_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return ((Number) obj).intValue();
      }
    };

    BOOLTOINT_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return ((Boolean) obj).booleanValue() ? 1 : 0;
      }
    };

    MATRIXTOINT_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return (int) ((MatrixLib) obj).elementAt(0, 0);
      }
    };

    TIMESTEPTOINT_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return ((Timestep) obj).getValue();
      }
    };

    STRINGTOINT_INTERP = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        return Integer.parseInt((String) obj);
      }
    };

    TO_INT = new TemplateFunction(TOINT_NAME) {
      @Override
      public FixedFunction getConcreteFunction(Type[] argTypes) {
        if (argTypes.length != 1) {
          return null;
        }
        FixedFunction func = null;
        Type ty = argTypes[0];
        if (ty.isSubtypeOf(BuiltInTypes.INTEGER)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.INTEGER),
              BuiltInTypes.INTEGER, NUMTOINT_INTERP);
        } else if (ty.isSubtypeOf(BuiltInTypes.REAL)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.REAL),
              BuiltInTypes.INTEGER, NUMTOINT_INTERP);
        } else if (ty.isSubtypeOf(BuiltInTypes.BOOLEAN)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.BOOLEAN),
              BuiltInTypes.INTEGER, BOOLTOINT_INTERP);
        } else if (ty.isSubtypeOf(BuiltInTypes.STRING)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.STRING),
              BuiltInTypes.INTEGER, STRINGTOINT_INTERP);
        } else if (ty.isSubtypeOf(BuiltInTypes.TIMESTEP)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.TIMESTEP),
              BuiltInTypes.INTEGER, TIMESTEPTOINT_INTERP);
        } else if (ty.isSubtypeOf(BuiltInTypes.REAL_MATRIX)) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.REAL_MATRIX),
              BuiltInTypes.INTEGER, MATRIXTOINT_INTERP);
        } else if (!ty.isBuiltIn()) {
          func = new FixedFunction(TOINT_NAME,
              Collections.singletonList(BuiltInTypes.TIMESTEP),
              BuiltInTypes.INTEGER, new AbstractFunctionInterp() {
                @Override
                public Object getValue(List args) {
                  Object obj = args.get(0);
                  return ((EnumeratedObject) obj).getIndex();
                }
              });
        }
        return func;
      }
    };
    addTemplate(TO_INT);

    FunctionInterp toRealInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        Object obj = args.get(0);
        if (obj instanceof Number) {
          return ((Number) obj).doubleValue();
        } else if (obj instanceof Boolean) {
          return ((Boolean) obj).booleanValue() ? 1 : 0;
        } else if (obj instanceof MatrixLib) {
          return ((MatrixLib) obj).elementAt(0, 0);
        } else if (obj instanceof Timestep) {
          return ((Timestep) obj).getValue();
        } else if (obj instanceof EnumeratedObject) {
          return ((EnumeratedObject) obj).getIndex();
        } else {
          double val = 0;
          try {
            val = Double.parseDouble(obj.toString());
          } catch (NumberFormatException e) {
            System.err.println(obj.toString() + " cannot be converted to Real");
            val = 0;
          }
          return val;
        }
      }
    };

    argTypes.clear();
    argTypes.add(BuiltInTypes.BUILT_IN);
    retType = BuiltInTypes.REAL;
    TO_REAL = new FixedFunction(TOREAL_NAME, argTypes, retType, toRealInterp);
    addFunction(TO_REAL);

    FunctionInterp loadRealMatrixInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        String filename = (String) args.get(0);
        MatrixLib mat = MatrixFactory.fromTxt(filename);
        if (args.size() == 1)
          return mat;
        // return a specific row
        if (args.size() == 2) {
          Integer row = (Integer) args.get(1);
          return mat.sliceRow(row.intValue());
        }
        // return consecutive rows
        if (args.size() <= 4) {
          Integer lo = (Integer) args.get(1);
          Integer hi = (Integer) args.get(2);
          return mat.sliceCols(lo.intValue(), hi.intValue());
        } else {
          // return submatrix
          Integer x1 = (Integer) args.get(1);
          Integer y1 = (Integer) args.get(2);
          Integer x2 = (Integer) args.get(3);
          Integer y2 = (Integer) args.get(4);
          return mat.subMat(x1.intValue(), y1.intValue(), x2.intValue(),
              y2.intValue());
        }
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    retType = BuiltInTypes.REAL_MATRIX;
    LOAD_REAL_MATRIX = new FixedFunction(LOAD_REAL_MATRIX_NAME, argTypes,
        retType, loadRealMatrixInterp);
    addFunction(LOAD_REAL_MATRIX);

    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    LOAD_REAL_MATRIX_ROW = new FixedFunction(LOAD_REAL_MATRIX_NAME, argTypes,
        retType, loadRealMatrixInterp);
    addFunction(LOAD_REAL_MATRIX_ROW);

    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    LOAD_REAL_MATRIX_ROWS = new FixedFunction(LOAD_REAL_MATRIX_NAME, argTypes,
        retType, loadRealMatrixInterp);
    addFunction(LOAD_REAL_MATRIX_ROWS);

    argTypes.clear();
    argTypes.add(BuiltInTypes.STRING);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL_MATRIX;
    LOAD_REAL_MATRIX_SUB = new FixedFunction(LOAD_REAL_MATRIX_NAME, argTypes,
        retType, loadRealMatrixInterp);
    addFunction(LOAD_REAL_MATRIX_SUB);

    /**
     * absolute value for Real
     */
    FunctionInterp absInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double val = ((Number) args.get(0)).doubleValue();
        return Math.abs(val);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;
    ABS = new FixedFunction(ABS_NAME, argTypes, retType, absInterp);
    addFunction(ABS);

    /**
     * absolute value for Integer
     */
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.INTEGER;
    ABS_INT = new FixedFunction(ABS_NAME, argTypes, retType, absInterp);
    addFunction(ABS_INT);

    /**
     * absolute value for Real matrix
     */
    FunctionInterp absMatInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return ((MatrixLib) args.get(0)).abs();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;
    ABS_MAT = new FixedFunction(ABS_NAME, argTypes, retType, absMatInterp);
    addFunction(ABS_MAT);

    /**
     * exponential function for real argument
     */
    FunctionInterp expInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double val = ((Number) args.get(0)).doubleValue();
        return Math.exp(val);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;
    EXP = new FixedFunction(EXP_NAME, argTypes, retType, expInterp);
    addFunction(EXP);

    /**
     * exponential function for integer argument
     */
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL;
    EXP_INT = new FixedFunction(EXP_NAME, argTypes, retType, expInterp);
    addFunction(EXP_INT);

    /**
     * exponential function for real matrix argument
     */
    FunctionInterp expMatInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return ((MatrixLib) args.get(0)).exp();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;
    EXP_MAT = new FixedFunction(EXP_NAME, argTypes, retType, expMatInterp);
    addFunction(EXP_MAT);

    /**
     * natural logarithm function for real argument
     */
    FunctionInterp logInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        double val = ((Number) args.get(0)).doubleValue();
        return Math.log(val);
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL);
    retType = BuiltInTypes.REAL;
    LOG = new FixedFunction(LOG_NAME, argTypes, retType, logInterp);
    addFunction(LOG);

    /**
     * natural logarithm function for integer argument
     */
    argTypes.clear();
    argTypes.add(BuiltInTypes.INTEGER);
    retType = BuiltInTypes.REAL;
    LOG_INT = new FixedFunction(LOG_NAME, argTypes, retType, logInterp);
    addFunction(LOG_INT);

    /**
     * natural logarithm function for real matrix argument
     */
    FunctionInterp logMatInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return ((MatrixLib) args.get(0)).log();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.REAL_MATRIX;
    LOG_MAT = new FixedFunction(LOG_NAME, argTypes, retType, logMatInterp);
    addFunction(LOG_MAT);

    FunctionInterp RowsInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return ((MatrixLib) args.get(0)).numRows();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.INTEGER;
    NUM_ROWS = new FixedFunction(NUMROWS_NAME, argTypes, retType, RowsInterp);
    addFunction(NUM_ROWS);

    FunctionInterp ColsInterp = new AbstractFunctionInterp() {
      public Object getValue(List args) {
        return ((MatrixLib) args.get(0)).numCols();
      }
    };
    argTypes.clear();
    argTypes.add(BuiltInTypes.REAL_MATRIX);
    retType = BuiltInTypes.INTEGER;
    NUM_COLS = new FixedFunction(NUMCOLS_NAME, argTypes, retType, ColsInterp);
    addFunction(NUM_COLS);
  };
}

/*
 * Author: yiwu
 * Date: 2014-4-22
 */
abstract class TemplateFunction {
  private String name;

  public TemplateFunction(String _name) {
    name = _name;
  }

  public String getName() {
    return name;
  }

  public abstract FixedFunction getConcreteFunction(Type[] argTypes);
}
