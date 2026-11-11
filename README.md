# Taller 3 — Calculadora y conversor (Java Swing)

Aplicación de escritorio desarrollada en Java con interfaz gráfica **Swing**. Permite realizar
operaciones matemáticas básicas y conversiones de temperatura y de moneda mediante botones,
con validación de los datos ingresados.

## Funcionalidades

### Operaciones matemáticas
- Suma, resta, multiplicación y división de dos números.
- La división valida que el divisor (campo «Número 2») sea distinto de cero.

### Conversión de temperatura
- Celsius a Fahrenheit.
- Fahrenheit a Celsius.
- Se rechazan valores por debajo del cero absoluto.

### Conversión de moneda
- Dólares a pesos colombianos.
- Pesos colombianos a dólares.
- Tasa fija: **1 USD = 3.200 COP**.

El resultado de cada operación se muestra en el panel inferior de la ventana y, además,
en un cuadro de diálogo.

## Requisitos del taller cubiertos

| Requisito | Dónde se cumple |
|---|---|
| `JFrame` | `VentanaPrincipal extends JFrame` |
| `JLabel` | Rótulos de campos y secciones |
| `JTextField` | Campos «Número 1», «Número 2», «Valor a convertir», «Monto a convertir» y resultado |
| `JButton` | Los ocho botones obligatorios y el botón «Limpiar» |
| `JOptionPane` | Mensajes de error, advertencia y resultado |
| `ActionListener` | `ManejadorEventos` controla todos los botones |
| Ingreso de números y valores | Campos independientes por sección |
| Botones obligatorios | Sumar, Restar, Multiplicar, Dividir, °C a °F, °F a °C, USD a COP, COP a USD |
| No permitir campos vacíos | Se avisa indicando el nombre exacto del campo y se enfoca |
| Validar datos numéricos | Filtro que bloquea letras y símbolos al escribir, más revalidación al calcular |
| Evitar división por cero | Mensaje de error y resultado limpio |
| Claridad de los datos | Cada sección tiene sus propios campos rotulados y una pista de uso |

## Estructura del proyecto

```
taller3/
├── pom.xml
├── README.md
├── .gitignore
└── src/main/java/com/taller3/
    ├── App.java                      # Punto de entrada y apariencia Nimbus
    ├── logica/Operaciones.java       # Operaciones y conversiones (tasa 3200)
    └── ui/VentanaPrincipal.java      # Interfaz Swing, validaciones y ActionListener
```

## Cómo compilar y ejecutar

Requisitos: JDK 21 o superior y Maven 3.9 o superior.

```bash
mvn clean package
java -jar target/taller3.jar
```

También se puede ejecutar directamente desde el IDE abriendo el proyecto Maven y ejecutando
la clase `com.taller3.App`.

## Uso

1. **Operaciones:** escriba los dos números en «Número 1» y «Número 2» y presione
   Sumar, Restar, Multiplicar o Dividir. En la división, «Número 2» es el divisor.
2. **Temperatura:** escriba el valor en «Valor a convertir» y presione **°C a °F** o
   **°F a °C**, según la unidad del dato ingresado.
3. **Moneda:** escriba el monto en «Monto a convertir» y presione **USD a COP** o
   **COP a USD**, según la moneda del dato ingresado.
4. **Limpiar** borra todos los campos y el resultado.

### Formato de los números
Se aceptan números enteros y decimales. El separador decimal puede ser punto o coma
(por ejemplo `1500.50` o `1500,50`). No se deben escribir separadores de miles.

## Validaciones implementadas

- Letras, espacios y caracteres especiales se bloquean mientras se escribe.
- Campos vacíos: advertencia con el nombre del campo y foco sobre él.
- Valores mal formados o no finitos: mensaje de error.
- División por cero: operación cancelada con mensaje de error.
- Temperaturas bajo el cero absoluto y montos negativos: advertencia.

## Historial de versiones

El proyecto se desarrolló en commits sucesivos que evidencian el avance: estructura inicial,
lógica de operaciones y, finalmente, la interfaz gráfica con sus validaciones.
