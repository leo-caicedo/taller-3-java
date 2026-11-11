package com.taller3.ui;

import com.taller3.logica.Operaciones;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Ventana principal de la calculadora y el conversor.
 *
 * <p>La ventana está dividida en tres secciones claramente rotuladas (operaciones,
 * temperatura y moneda) y un panel de resultado común. Cada sección tiene sus propios
 * campos y botones, de modo que el usuario siempre sabe dónde ingresar los datos.</p>
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Locale LOCALE_CO = Locale.forLanguageTag("es-CO");
    private static final DecimalFormat FORMATO_NUMERO =
            new DecimalFormat("#,##0.##", DecimalFormatSymbols.getInstance(LOCALE_CO));
    private static final DecimalFormat FORMATO_MONEDA =
            new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(LOCALE_CO));

    private static final String CMD_SUMAR = "SUMAR";
    private static final String CMD_RESTAR = "RESTAR";
    private static final String CMD_MULTIPLICAR = "MULTIPLICAR";
    private static final String CMD_DIVIDIR = "DIVIDIR";
    private static final String CMD_CELSIUS_A_FAHRENHEIT = "CELSIUS_A_FAHRENHEIT";
    private static final String CMD_FAHRENHEIT_A_CELSIUS = "FAHRENHEIT_A_CELSIUS";
    private static final String CMD_USD_A_COP = "USD_A_COP";
    private static final String CMD_COP_A_USD = "COP_A_USD";
    private static final String CMD_LIMPIAR = "LIMPIAR";

    private static final double CERO_ABSOLUTO_CELSIUS = -273.15;
    private static final double CERO_ABSOLUTO_FAHRENHEIT = -459.67;

    private final JTextField campoNumero1 = crearCampo(true);
    private final JTextField campoNumero2 = crearCampo(true);
    private final JTextField campoTemperatura = crearCampo(true);
    private final JTextField campoMonto = crearCampo(false);
    private final JTextField campoResultado = crearCampoResultado();

    private final ActionListener manejador = new ManejadorEventos();

    public VentanaPrincipal() {
        super("Taller 3 - Calculadora y Conversor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(crearContenido());
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------------
    // Construcción de la interfaz
    // ------------------------------------------------------------------

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(0, 14));
        contenido.setBorder(new EmptyBorder(16, 16, 16, 16));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(crearSecciones(), BorderLayout.CENTER);
        contenido.add(crearPanelResultado(), BorderLayout.SOUTH);
        return contenido;
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Calculadora y conversor");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, titulo.getFont().getSize2D() + 6f));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instrucciones = new JLabel(
                "<html>Escriba los datos en el campo de la sección correspondiente "
                        + "y presione el botón de la operación deseada.</html>");
        instrucciones.setFont(instrucciones.getFont().deriveFont(Font.PLAIN));
        instrucciones.setForeground(new Color(0x50, 0x50, 0x50));
        instrucciones.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(6));
        encabezado.add(instrucciones);
        return encabezado;
    }

    private JPanel crearSecciones() {
        JPanel secciones = new JPanel();
        secciones.setLayout(new BoxLayout(secciones, BoxLayout.Y_AXIS));
        secciones.add(crearSeccionOperaciones());
        secciones.add(Box.createVerticalStrut(12));
        secciones.add(crearSeccionTemperatura());
        secciones.add(Box.createVerticalStrut(12));
        secciones.add(crearSeccionMoneda());
        return secciones;
    }

    private JPanel crearSeccionOperaciones() {
        JPanel seccion = crearSeccion("Operaciones matemáticas");

        agregar(seccion, new JLabel("Número 1:"), 0, 0, 1, 0.0);
        agregar(seccion, campoNumero1, 1, 0, 1, 1.0);
        agregar(seccion, crearPista("primer operando"), 2, 0, 1, 0.0);

        agregar(seccion, new JLabel("Número 2:"), 0, 1, 1, 0.0);
        agregar(seccion, campoNumero2, 1, 1, 1, 1.0);
        agregar(seccion, crearPista("segundo operando (divisor al dividir)"), 2, 1, 1, 0.0);

        agregar(seccion, crearFilaBotones(
                crearBoton("Sumar", CMD_SUMAR),
                crearBoton("Restar", CMD_RESTAR),
                crearBoton("Multiplicar", CMD_MULTIPLICAR),
                crearBoton("Dividir", CMD_DIVIDIR)), 0, 2, 3, 1.0);

        return seccion;
    }

    private JPanel crearSeccionTemperatura() {
        JPanel seccion = crearSeccion("Conversión de temperatura");

        agregar(seccion, new JLabel("Valor a convertir:"), 0, 0, 1, 0.0);
        agregar(seccion, campoTemperatura, 1, 0, 1, 1.0);

        agregar(seccion, crearPista(
                "El valor se interpreta en la unidad del botón que presione (°C o °F)."), 0, 1, 3, 1.0);

        agregar(seccion, crearFilaBotones(
                crearBoton("°C a °F", CMD_CELSIUS_A_FAHRENHEIT),
                crearBoton("°F a °C", CMD_FAHRENHEIT_A_CELSIUS)), 0, 2, 3, 1.0);

        return seccion;
    }

    private JPanel crearSeccionMoneda() {
        JPanel seccion = crearSeccion(
                "Conversión de moneda (tasa fija: $ 3.200 COP = 1 USD)");

        agregar(seccion, new JLabel("Monto a convertir:"), 0, 0, 1, 0.0);
        agregar(seccion, campoMonto, 1, 0, 1, 1.0);

        agregar(seccion, crearPista(
                "El monto se interpreta en la moneda del botón que presione (USD o COP)."), 0, 1, 3, 1.0);

        agregar(seccion, crearFilaBotones(
                crearBoton("USD a COP", CMD_USD_A_COP),
                crearBoton("COP a USD", CMD_COP_A_USD)), 0, 2, 3, 1.0);

        return seccion;
    }

    private JPanel crearPanelResultado() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Resultado"));

        JButton botonLimpiar = crearBoton("Limpiar", CMD_LIMPIAR);

        panel.add(campoResultado, BorderLayout.CENTER);
        panel.add(botonLimpiar, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearSeccion(String titulo) {
        JPanel seccion = new JPanel(new GridBagLayout());
        seccion.setBorder(BorderFactory.createTitledBorder(titulo));
        return seccion;
    }

    private void agregar(JPanel panel, Component componente, int x, int y, int ancho, double pesoX) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = ancho;
        gbc.weightx = pesoX;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 6, 4, 6);
        panel.add(componente, gbc);
    }

    private JPanel crearFilaBotones(JButton... botones) {
        JPanel fila = new JPanel(new GridLayout(1, botones.length, 8, 0));
        for (JButton boton : botones) {
            fila.add(boton);
        }
        return fila;
    }

    private JButton crearBoton(String texto, String comando) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
        boton.addActionListener(manejador);
        return boton;
    }

    private static JTextField crearCampo(boolean permiteNegativos) {
        JTextField campo = new JTextField(14);
        campo.setHorizontalAlignment(JTextField.RIGHT);
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new FiltroNumerico(permiteNegativos));
        return campo;
    }

    private static JTextField crearCampoResultado() {
        JTextField campo = new JTextField(16);
        campo.setEditable(false);
        campo.setFocusable(false);
        campo.setHorizontalAlignment(JTextField.RIGHT);
        campo.setFont(campo.getFont().deriveFont(Font.BOLD, campo.getFont().getSize2D() + 2f));
        campo.setToolTipText("Aquí se muestra el resultado de la operación.");
        return campo;
    }

    private static JLabel crearPista(String texto) {
        JLabel pista = new JLabel(texto);
        pista.setFont(pista.getFont().deriveFont(Font.PLAIN, pista.getFont().getSize2D() - 1f));
        pista.setForeground(new Color(0x5A, 0x5A, 0x5A));
        return pista;
    }

    // ------------------------------------------------------------------
    // Manejo de eventos
    // ------------------------------------------------------------------

    private final class ManejadorEventos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            switch (evento.getActionCommand()) {
                case CMD_SUMAR -> sumar();
                case CMD_RESTAR -> restar();
                case CMD_MULTIPLICAR -> multiplicar();
                case CMD_DIVIDIR -> dividir();
                case CMD_CELSIUS_A_FAHRENHEIT -> convertirCelsiusAFahrenheit();
                case CMD_FAHRENHEIT_A_CELSIUS -> convertirFahrenheitACelsius();
                case CMD_USD_A_COP -> convertirUsdACop();
                case CMD_COP_A_USD -> convertirCopAUsd();
                case CMD_LIMPIAR -> limpiarTodo();
                default -> {
                    // No hay más acciones registradas.
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Operaciones matemáticas
    // ------------------------------------------------------------------

    private void sumar() {
        Double a = leerValor(campoNumero1, "Número 1");
        if (a == null) {
            return;
        }
        Double b = leerValor(campoNumero2, "Número 2");
        if (b == null) {
            return;
        }
        mostrarResultado(FORMATO_NUMERO.format(a) + " + " + FORMATO_NUMERO.format(b)
                + " = " + FORMATO_NUMERO.format(Operaciones.sumar(a, b)));
    }

    private void restar() {
        Double a = leerValor(campoNumero1, "Número 1");
        if (a == null) {
            return;
        }
        Double b = leerValor(campoNumero2, "Número 2");
        if (b == null) {
            return;
        }
        mostrarResultado(FORMATO_NUMERO.format(a) + " - " + FORMATO_NUMERO.format(b)
                + " = " + FORMATO_NUMERO.format(Operaciones.restar(a, b)));
    }

    private void multiplicar() {
        Double a = leerValor(campoNumero1, "Número 1");
        if (a == null) {
            return;
        }
        Double b = leerValor(campoNumero2, "Número 2");
        if (b == null) {
            return;
        }
        mostrarResultado(FORMATO_NUMERO.format(a) + " × " + FORMATO_NUMERO.format(b)
                + " = " + FORMATO_NUMERO.format(Operaciones.multiplicar(a, b)));
    }

    private void dividir() {
        Double dividendo = leerValor(campoNumero1, "Número 1");
        if (dividendo == null) {
            return;
        }
        Double divisor = leerValor(campoNumero2, "Número 2");
        if (divisor == null) {
            return;
        }
        if (divisor == 0) {
            mostrarError("No es posible dividir por cero.\n"
                    + "Ingrese un valor distinto de 0 en el campo «Número 2».");
            campoResultado.setText("");
            enfocar(campoNumero2);
            return;
        }
        mostrarResultado(FORMATO_NUMERO.format(dividendo) + " ÷ " + FORMATO_NUMERO.format(divisor)
                + " = " + FORMATO_NUMERO.format(Operaciones.dividir(dividendo, divisor)));
    }

    // ------------------------------------------------------------------
    // Conversión de temperatura
    // ------------------------------------------------------------------

    private void convertirCelsiusAFahrenheit() {
        Double celsius = leerValor(campoTemperatura, "Valor a convertir");
        if (celsius == null) {
            return;
        }
        if (celsius < CERO_ABSOLUTO_CELSIUS) {
            mostrarAdvertencia("La temperatura no puede ser inferior al cero absoluto "
                    + "(-273,15 °C).\nVerifique el valor ingresado.");
            enfocar(campoTemperatura);
            return;
        }
        double fahrenheit = Operaciones.celsiusAFahrenheit(celsius);
        mostrarResultado(FORMATO_NUMERO.format(celsius) + " °C = "
                + FORMATO_NUMERO.format(fahrenheit) + " °F");
    }

    private void convertirFahrenheitACelsius() {
        Double fahrenheit = leerValor(campoTemperatura, "Valor a convertir");
        if (fahrenheit == null) {
            return;
        }
        if (fahrenheit < CERO_ABSOLUTO_FAHRENHEIT) {
            mostrarAdvertencia("La temperatura no puede ser inferior al cero absoluto "
                    + "(-459,67 °F).\nVerifique el valor ingresado.");
            enfocar(campoTemperatura);
            return;
        }
        double celsius = Operaciones.fahrenheitACelsius(fahrenheit);
        mostrarResultado(FORMATO_NUMERO.format(fahrenheit) + " °F = "
                + FORMATO_NUMERO.format(celsius) + " °C");
    }

    // ------------------------------------------------------------------
    // Conversión de moneda
    // ------------------------------------------------------------------

    private void convertirUsdACop() {
        Double dolares = leerValor(campoMonto, "Monto a convertir");
        if (dolares == null) {
            return;
        }
        if (dolares < 0) {
            mostrarAdvertencia("El monto no puede ser negativo.");
            enfocar(campoMonto);
            return;
        }
        double pesos = Operaciones.usdACop(dolares);
        mostrarResultado(FORMATO_MONEDA.format(dolares) + " USD = $ "
                + FORMATO_MONEDA.format(pesos) + " COP");
    }

    private void convertirCopAUsd() {
        Double pesos = leerValor(campoMonto, "Monto a convertir");
        if (pesos == null) {
            return;
        }
        if (pesos < 0) {
            mostrarAdvertencia("El monto no puede ser negativo.");
            enfocar(campoMonto);
            return;
        }
        double dolares = Operaciones.copAUsd(pesos);
        mostrarResultado("$ " + FORMATO_MONEDA.format(pesos) + " COP = "
                + FORMATO_MONEDA.format(dolares) + " USD");
    }

    // ------------------------------------------------------------------
    // Utilidades de validación y presentación
    // ------------------------------------------------------------------

    /**
     * Lee y valida el valor de un campo. Devuelve {@code null} cuando el campo está vacío,
     * contiene un valor no numérico o no es un número finito, mostrando el mensaje
     * correspondiente al usuario.
     */
    private Double leerValor(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            mostrarAdvertencia("El campo «" + nombreCampo + "» no puede estar vacío.\n"
                    + "Ingrese un valor numérico para continuar.");
            enfocar(campo);
            return null;
        }
        try {
            double valor = Double.parseDouble(texto.replace(',', '.'));
            if (!Double.isFinite(valor)) {
                throw new NumberFormatException("Valor no finito");
            }
            return valor;
        } catch (NumberFormatException ex) {
            mostrarError("El valor ingresado en «" + nombreCampo + "» no es un número válido.\n"
                    + "Use solo dígitos y, si lo necesita, un punto o una coma decimal.");
            enfocar(campo);
            campo.selectAll();
            return null;
        }
    }

    private void mostrarResultado(String detalle) {
        campoResultado.setText(detalle);
        JOptionPane.showMessageDialog(this, detalle, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos incompletos o no válidos",
                JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void enfocar(JTextField campo) {
        campo.requestFocusInWindow();
    }

    private void limpiarTodo() {
        campoNumero1.setText("");
        campoNumero2.setText("");
        campoTemperatura.setText("");
        campoMonto.setText("");
        campoResultado.setText("");
        campoNumero1.requestFocusInWindow();
    }

    /**
     * Filtro que impide escribir letras, espacios y símbolos especiales en los campos
     * numéricos. Solo admite dígitos, un único separador decimal (punto o coma) y,
     * cuando el campo lo permite, un signo menos inicial.
     */
    private static final class FiltroNumerico extends DocumentFilter {

        private static final int LONGITUD_MAXIMA = 15;

        private final boolean permiteNegativos;

        private FiltroNumerico(boolean permiteNegativos) {
            this.permiteNegativos = permiteNegativos;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String texto, AttributeSet atributos)
                throws BadLocationException {
            aplicarCambio(fb, offset, 0, texto == null ? "" : texto, atributos, true);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int longitud, String texto,
                            AttributeSet atributos) throws BadLocationException {
            aplicarCambio(fb, offset, longitud, texto == null ? "" : texto, atributos, false);
        }

        private void aplicarCambio(FilterBypass fb, int offset, int longitud, String texto,
                                   AttributeSet atributos, boolean insercion)
                throws BadLocationException {
            String actual = fb.getDocument().getText(0, fb.getDocument().getLength());
            int fin = Math.min(offset + longitud, actual.length());
            String resultado = actual.substring(0, Math.min(offset, actual.length()))
                    + texto + actual.substring(fin);

            if (!esValido(resultado)) {
                return;
            }
            if (insercion) {
                super.insertString(fb, offset, texto, atributos);
            } else {
                super.replace(fb, offset, longitud, texto, atributos);
            }
        }

        private boolean esValido(String texto) {
            if (texto.length() > LONGITUD_MAXIMA) {
                return false;
            }
            String cuerpo = texto;
            if (cuerpo.startsWith("-")) {
                if (!permiteNegativos) {
                    return false;
                }
                cuerpo = cuerpo.substring(1);
            }
            boolean separadorVisto = false;
            for (int i = 0; i < cuerpo.length(); i++) {
                char caracter = cuerpo.charAt(i);
                if (caracter == '.' || caracter == ',') {
                    if (separadorVisto) {
                        return false;
                    }
                    separadorVisto = true;
                } else if (caracter < '0' || caracter > '9') {
                    return false;
                }
            }
            return true;
        }
    }
}
