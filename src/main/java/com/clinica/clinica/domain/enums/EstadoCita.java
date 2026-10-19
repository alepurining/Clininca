//Las enumeraciones en Java son un tipo especial de clase que representa un conjunto de constantes. En este caso, la enumeración EstadoCita define los posibles estados que puede tener una cita en el sistema de la clínica. Los estados definidos son: Programada, Confirmada, Cancelada y Realizada. Esta enumeración se utiliza para garantizar que las citas solo puedan tener uno de estos valores predefinidos, lo que ayuda a mantener la integridad de los datos y facilita la gestión del flujo de trabajo relacionado con las citas médicas.

package com.clinica.clinica.domain.enums;

public enum EstadoCita {
    Programada,
    Confirmada,
    Cancelada,
    Realizada
}
