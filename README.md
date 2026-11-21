# MegaComanda

MegaComanda es una aplicación de escritorio desarrollada en Java para facilitar el registro y seguimiento de los pedidos del restaurante Mega Alitas Express, ubicado en Montería. La idea del proyecto es organizar el trabajo entre meseros, cocina y caja, evitando depender únicamente de las comandas de papel para conocer el estado de los pedidos.

El sistema permite registrar comandas por mesa, agregar productos, cantidades y observaciones, enviar los pedidos a cocina y consultar los cambios de estado hasta su entrega. También cuenta con un módulo de caja para llevar el control de los pagos realizados.

## Requisitos

Para ejecutar el proyecto se necesita Java JDK 17 o una versión superior. El proyecto incluye un archivo `pom.xml`, por lo que puede abrirse desde IntelliJ IDEA como un proyecto Maven.

## Cómo ejecutar el programa

Primero se debe descomprimir la carpeta del proyecto y abrirla desde IntelliJ IDEA. Una vez cargado el proyecto, se debe comprobar que esté configurado el JDK 17 o superior y esperar a que Maven reconozca las dependencias.

Después se abre la clase `Main.java`, ubicada en `src/main/java/com/megacomanda/`, y se ejecuta el método principal para iniciar MegaComanda.

## Funcionamiento

Al iniciar el programa se encuentran las opciones para registrar comandas y consultar las vistas de cocina, mesero y caja. Para crear un pedido se selecciona una mesa, se agregan los productos y sus cantidades, y se escriben las observaciones necesarias antes de enviarlo a cocina.

Desde la vista de cocina se pueden consultar los pedidos recibidos y marcar como listo aquel que ya terminó su preparación. El mesero puede revisar el cambio de estado y registrar la entrega del pedido. Si se necesita cancelar una comanda que ya fue enviada, se puede realizar una solicitud para que cocina la acepte o la rechace.

En la sección de caja se consultan las comandas entregadas y se registran los pagos. El sistema permite realizar pagos completos, abonos parciales a una comanda y abonos generales a la cuenta de una mesa. Cuando se registra un abono general, el valor se distribuye entre las comandas que tienen saldo pendiente, comenzando por la más antigua.

Los métodos de pago disponibles son efectivo y transferencia. También se puede consultar el historial de pagos registrados.

## Patrón de diseño Observer

MegaComanda utiliza el patrón Observer para mantener actualizadas las diferentes vistas cuando se producen cambios en las comandas. `TableroComandas` funciona como sujeto y se encarga de notificar a los observadores registrados.

La interfaz `ObservadorPedido` define el método de actualización, mientras que `PanelObservador` implementa ese comportamiento. Las vistas de cocina, mesero y caja reciben las notificaciones para mostrar la información actualizada sin que el tablero tenga que conocer los detalles internos de cada una.

## Organización del proyecto

El código fuente se encuentra en `src/main/java/com/megacomanda/`. Allí están organizadas las clases del modelo, los servicios, las vistas, el patrón Observer y la persistencia de la información.

La carpeta `modelo` contiene las clases relacionadas con las mesas, productos, pedidos y pagos. En `servicios` se encuentra la lógica para gestionar las comandas y sus pagos. La carpeta `observer` contiene los componentes encargados de las notificaciones. En `vistas` están las interfaces de registro, cocina, mesero y caja. Finalmente, `persistencia` contiene la clase encargada de registrar los movimientos en archivos CSV.

## Almacenamiento de la información

Las comandas se mantienen en memoria mientras la aplicación está en ejecución. Los movimientos de las comandas se registran en `bitacora_mega_comanda.csv`, mientras que los pagos se guardan en `pagos_mega_comanda.csv`.

Estos archivos permiten conservar un registro de los movimientos y pagos realizados. Sin embargo, las comandas no se recuperan automáticamente al cerrar y volver a iniciar el programa.

MegaComanda fue desarrollado como proyecto académico para la asignatura Programación III de la Universidad de Córdoba, con el propósito de aplicar conceptos de programación orientada a objetos y el patrón de diseño Observer.