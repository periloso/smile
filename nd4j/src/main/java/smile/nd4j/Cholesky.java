/*
 * Copyright (c) 2010-2020 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */

package smile.nd4j;

import smile.math.matrix.Matrix;
import smile.math.matrix.DenseMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Cholesky decomposition is a decomposition of a symmetric, positive-definite
 * matrix into a lower triangular matrix L and the transpose of the lower
 * triangular matrix such that A = L*L'. The lower triangular matrix is the
 * Cholesky triangle of the original, positive-definite matrix. When it is
 * applicable, the Cholesky decomposition is roughly twice as efficient as the LU
 * decomposition for solving systems of linear equations.
 * <p>
 * The Cholesky decomposition is mainly used for the numerical solution of
 * linear equations. The Cholesky decomposition is also commonly used in
 * the Monte Carlo method for simulating systems with multiple correlated
 * variables: The matrix of inter-variable correlations is decomposed,
 * to give the lower-triangular L. Applying this to a vector of uncorrelated
 * simulated shocks, u, produces a shock vector Lu with the covariance
 * properties of the system being modeled.
 * <p>
 * Unscented Kalman filters commonly use the Cholesky decomposition to choose
 * a set of so-called sigma points. The Kalman filter tracks the average
 * state of a system as a vector x of length n and covariance as an n-by-n
 * matrix P. The matrix P is always positive semi-definite, and can be
 * decomposed into L*L'. The columns of L can be added and subtracted from
 * the mean x to form a set of 2n vectors called sigma points. These sigma
 * points completely capture the mean and covariance of the system state.
 * <p>
 * If the matrix is not positive definite, an exception will be thrown out from
 * the method decompose().
 *
 * @author Haifeng Li
 */
class Cholesky extends smile.math.matrix.Cholesky {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Cholesky.class);

    /**
     * Constructor.
     * @param  L the lower triangular part of matrix contains the Cholesky
     *          factorization.
     */
    public Cholesky(NDMatrix L) {
        super(L);
    }

    /**
     * Returns the matrix inverse.
     */
    public DenseMatrix inverse() {
        int n = L.nrow();
        DenseMatrix inv = Matrix.eye(n);
        solve(inv);
        return inv;
    }

    /**
     * Solve the linear system A * x = b. On output, b will be overwritten with
     * the solution vector.
     * @param  b   the right hand side of linear systems. On output, b will be
     * overwritten with solution vector.
     */
    public void solve(double[] b) {
        // B use b as the internal storage. Therefore b will contains the results.
        DenseMatrix B = Matrix.of(b);
        solve(B);
    }

    /**
     * Solve the linear system A * X = B. On output, B will be overwritten with
     * the solution matrix.
     * @param  B   the right hand side of linear systems.
     */
    public void solve(DenseMatrix B) {
        if (B.nrow() != L.nrow()) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", L.nrow(), L.ncol(), B.nrow(), B.ncol()));
        }

        throw new UnsupportedOperationException("dpotrs is not supported by ND4J");
        /*
        intW info = new intW(0);
        LAPACK.getInstance().dpotrs(NLMatrix.Lower, L.nrow(), B.ncol(), L.data(), L.ld(), B.data(), B.ld(), info);

        if (info.val < 0) {
            logger.error("LAPACK DPOTRS error code: {}", info.val);
            throw new IllegalArgumentException("LAPACK DPOTRS error code: " + info.val);
        }
        */
    }
}

