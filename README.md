# GeneradorMarcas
El proyecto consta de la propuesta y construcción de una aplicación distribuida que se compone de dos partes: Una aplicación servidora y una aplicación cliente.

>Descripción de la aplicación servidora
>>Recepción de tickets
La aplicación servidora al iniciarse, procesa un archivo en Excel que deberá llamarse TICKETS.xls, en el cual se simula la recepción de un conjunto de registros que representan el ingreso de tickets o inconformidades registrados por clientes asociados a la empresa. 
Al momento de leer los datos el servidor sólo se interesa por obtener la fecha y hora en la que se recibe el ticket, el id del cliente y el asunto. La aplicación servidora cuenta con una “carpeta INBOX” donde se reciben todas las solicitudes de atención de tickets que provienen de clientes que se han conectado para reportar una inconformidad. Esta carpeta se alimenta por medio de una acción que tiene el servidor para cargar nuevos tickets y que sólo puede ser ejecutada por el administrador.
El id del ticket es un número secuencial generado automáticamente en el momento de recepción por parte del servidor. 
Al momento de ingreso y recepción de los tickets, se registran con un estado de PENDIENTE. 
Una vez recibidos y cargados por el servidor, se presentarán en una pantalla del servidor, donde un usuario tipo ADMINISTRADOR (que se encargó de levantar el servidor) puede clasificarlos en tres tipos de categorías representadas por un colo.
Este proceso se puede realizar procesando los tickets por bloques (seleccionando uno o varios manualmente y asignando una categoría). Considere la posibilidad de generar este proceso automático.
La determinación del color o grado de intensidad del ticket es definida por el administrador y sólo puede ser realizada a los tickets recién ingresados (con estado de PENDIENTE).
Una vez asignada la categoría, este proceso provoca que los tickets se muevan de la carpeta INBOX a una de tres colas: COLA_ROJA, COLA_AMARILLA, COLA_VERDE dejando la carpeta INBOX únicamente con aquellos tickets que no se hayan categorizado. 
>>Los colaboradores
En la aplicación servidora, existe registrado un conjunto de colaboradores que se conectarán al servidor. Estos colaboradores serán cada uno de los miembros del equipo de trabajo y cada uno de ellos cuenta con:
>>>1. Un login
>>>2. Una contraseña 
3>>>. Nombre completo 
>>>4. Categoría actual de trabajo (ROJA, VERDE, AMARILLA) la cual denota la especialidad de atención de los tickets.
>>>5. Estado (CONECTADO, DESCONECTADO) 
>>Los servicios
El servidor puede atender las siguientes peticiones por parte de las aplicaciones Cliente:
>>>1. Conectar empleado: Los empleados se conectan al servidor desde una aplicación cliente y si las credenciales son correctas, en el servidor el empleado se modifica como CONECTADO. Esta acción se identificará con el código CONECTAR_EMPLEADO. 
>>>2. Solicitar tickets que aún no han sido atendidos de acuerdo al color del cliente conectado (ROJO, VERDE, AMARILLO): En esta acción se recibe como parte de la petición el color y se devuelve al cliente una lista con los tickets solicitados. Esta acción se identificará por el código SOLICITAR_TICKETS. 
>>>3. Recibir la indicación de que un ticket se comenzará a atender, para lo cual recibe el número de ticket y procede a modificar su estado pasándolo de PENDIENTE a EN ATENCION retornando un valor de TRUE al cliente lo que le permite garantizarse que sólo el podrá modificar su información de atención. Si el ticket al momento de modificar su estado no se encuentra en estado de PENDIENTE entonces no podrá modificar su estado y por lo tanto retorna FALSE. Esta acción se identificará por el código RESERVAR_TICKET.
>>>4. Registrar los detalles de atención de ticket, para lo cual en la aplicación cliente se envía completa la queja atendida que reporta: el usuario que a atendió, el tiempo invertido en la resolución expresado en segundos, un comentario sobre la resolución, la fecha y hora de resolución. Esto provoca que los datos de atención se actualicen en la queja indicada y el estado pase de EN ATENCION a ATENDIDO por lo que ya no será seleccionada por ningún otro empleado de la misma categoría. Cuando esto ocurre el servidor debe calcular una métrica de atención. Esta métrica es diferente para cada uno de los tipos de tickets. Se calcula de la siguiente manera:
>>Verde: tiempo invertido - 10
>>Amarillo: tiempo invertido * 2 - 12
>>Rojo: tiempo invertido * 3 - 15
Esta acción se identificará por el código ACTUALIZAR_TICKET. 
>>>5. Liberar un ticket pues no se puede resolver: en este caso el cliente envía el número del ticket que anteriormente solicitó para darle atención, lo que provocó que estuviera en estado EN ATENCION pero por alguna razón se debe devolver a estado PENDIENTE para permitir que otro encargado la tramite. Esta acción se identificará por el código LIBERAR_TICKET. 
>>>6. Solicitar reporte de atención, en el cual el servidor recibirá el login del empleado y una fecha específica y retornará la información necesaria al empleado conectado sobre su tasa de atención para la fecha indicada, esto es, de la cantidad de tickets recibidos en esta fecha cuál la cantidad de tickets atendidos en forma satisfactoria por él (en cantidad y proporción, es decir si la totalidad de tickets recibidos en una fecha fueron 20 y él atendió satisfactoriamente 5, debería reportar los valores 20 recibidos, 5 atendidos, 25% de proporción). Esta acción se identificará como REPORTE_TICKET.
>>>7. Desconectar empleado: En este servicio el servidor localiza el id del empleado indicado y procede a modificar su estado como DESCONECTADO por lo que no podrá hacer uso de los otros servicios del servidor. Esta acción se identificará como DESCONECTAR_EMPLEADO. Cada vez que ocurre un cambio en las quejas cargadas en el servidor deberán refrescarse para tener la información lo más actualizada posible en la pantalla del mismo. 
>>El monitoreo del servidor 
La aplicación de atención a tickets al ser iniciada procede a levantar su servidor para permitir que las aplicaciones cliente que se encargarán de procesar los tickets recibidos puedan establecer contacto con él. Por lo tanto, esta aplicación cuenta con una pantalla de monitoreo donde se mostrará constantemente el estado de comunicación del servidor con los clientes conectados y las acciones que éstos están solicitando a cada instante. El servidor además debe mostrar constantemente el estado gráfico de las listas de trabajo (INBOX, COLA_ROJA, COLA_AMARILLA, COLA_VERDE) y el estado de los tickets contenidos en ellas. 
>>Las consultas en el servidor 
Además el servidor puede ofrecer información significativa como:
● ¿Cuál es el tiempo promedio de atención de tickets en general y por cada empleado?
● Listar los tickets atendidas en un rango de fechas sin importar las categorías.
● Listar los tickets atendidos satisfactoriamente por un empleado particular. 
>>Bajar el servidor
Al bajar el servidor y salir de la aplicación, deberá quedar en un archivo de Excel la información de cada una de las colas (ROJA, AMARILLA, VERDE) con los tiquetes tramitados al momento y en INBOX. Cada uno de ellos deberá quedar en una pestaña distinta del archivo de Excel donde cada pestaña se llame ROJA, AMARILLA, VERDE, INBOX. El nombre del archivo que se guarda sería TICKETS_Fecha.xls Ejemplo TICKETS_30042015.xls
La aplicación cliente
En el caso de la aplicación cliente, ésta inicia indicando el nombre del usuario que se conecta y cuenta con un selector de color (ROJO, AMARILLO, VERDE) que le permite indicar el tipo de queja que se dispone a atender. Al iniciar la ejecución del cliente cada aplicación cliente debe solicitar el host y el puerto al que se debe conectar para establecer comunicación con la aplicación servidora. Debe crear una clase con la información de conexión y debe asegurar que exista un único servidor.
El encargado de operar la aplicación cliente tiene como acciones disponibles las siguientes:
>>1. Conectarse al servidor: Solicita el servicio CONECTAR_EMPLEADO enviado su login y contraseña y si son correctas según el servidor, la aplicación cliente ingresa al modo de sesión de trabajo indicando en pantalla su nombre completo, pudiendo marcar en un selector de color, la categoría actual de trabajo. 
>>2. Solicitar la lista de tickets pendientes de atención según el color definido: esto provoca que se conecte al servidor por medio de la operación SOLICITAR_TICKETS y visualizar la lista de tickets con estado PENDIENTE en el servidor, las cuales se muestran en una tabla visual en pantalla.
>>3. Al seleccionar un ticket específico los datos del mismo se cargan en un espacio en la pantalla diseñado para este efecto.
>>4. El cliente tiene en pantalla un reloj o cronómetro que se inicializa en un cierto momento, se puede pausar y reanudar en cualquier momento.
>>5. Cuando decide comenzar a atender un ticket específico, el cliente envía una petición RESERVAR_TICKET al servidor para que lo marque como EN ATENCION. Si el servidor reporta éxito en la solicitud el cronómetro comienza a transcurrir midiendo el tiempo invertido
en la atención de la misma. El cronómetro se pausa y se reanuda las veces que así lo requiera y el tiempo sigue acumulándose.
>>6. Si el cliente resuelve el ticket actual, envía los datos de la resolución por medio de la acción ACTUALIZAR_TICKETS en la que envía todos los datos necesarios para que el servidor pueda marcarlo como ATENDIDO.
>>7. Si decide que no puede resolverla puede solicitar la acción de LIBERAR_TICKET pero igual registra el tiempo dedicado aunque no haya sido resuelta y además envía una razón del porqué lo abandona sin éxito, mensaje que se mantiene en el comentario de resolución como un log de trámite de la misma.
>>8. Cada cierto tiempo y sin que el encargado lo solicite por medio de una acción la aplicación cliente deberá solicitar al servidor refrescar la lista de tickets del color actualmente atendido con el objetivo de poder visualizar los cambios que están realizando otros clientes conectados. Esto lo hace por medio de la acción SOLICITAR_TICKET que le devuelve la lista de quejas pendientes de manera que si en pantalla tenía una pendiente y ya fue atendida debería notarse el cambio en el estado y no debería permitirse su selección. Además debe mantener visible el ticket actualmente tramitado.
>>9. El usuario conectado puede solicitar al servidor un estado de su trabajo mediante el servicio REPORTE_TICKET en el cual por medio de su login y una fecha puede conocer su proporción de efectividad de acuerdo a la cantidad de tickets atendidos satisfactoriamente.
>>10. El usuario puede solicitar el abandono de la sesión por medio del servicio DESCONECTAR_USUARIO
