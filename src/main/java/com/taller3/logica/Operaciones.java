package com.taller3.logica;

/**
 * Contiene la lógica de las operaciones matemáticas y de las conversiones.
 * Está separada de la interfaz gráfica para poder probarla y reutilizarla con facilidad.
 */
public final class Operaciones {

    /** Tasa fija exigida por el taller: 1 dólar equivale a 3.200 pesos colombianos. */
    public static final double TASA_USD_COP = 3200.0;

    private Operaciones() {
        // Clase de utilidades: no se instancia.
    }

    public static double sumar(double a, double b) {
        return a + b;
    }

    public static double restar(double a, double b) {
        return a - b;
    }

    public static double multiplicar(double a, double b) {
        return a * b;
    }

    /**
     * Divide dos números.
     *
     * @throws ArithmeticException si el divisor es cero.
     */
    public static double dividir(double dividendo, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("No es posible dividir por cero.");
        }
        return dividendo / divisor;
    }

    public static double celsiusAFahrenheit(double celsius) {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    public static double fahrenheitACelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public static double usdACop(double dolares) {
        return dolares * TASA_USD_COP;
    }

    public static double copAUsd(double pesos) {
        return pesos / TASA_USD_COP;
    }
}
