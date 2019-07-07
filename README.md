# GeneradorMarcas
El proyecto consta de la propuesta y construcción de una aplicación distribuida que se compone de dos partes: Una aplicación servidora y una aplicación cliente.

>La aplicación cliente
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
