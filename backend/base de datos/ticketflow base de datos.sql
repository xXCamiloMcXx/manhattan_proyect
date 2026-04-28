create table usuarios(

    id bigserial not null,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    telefono varchar(20) not null,
    cedula varchar(12) not null unique,
    fecha_nacimiento timestamp not null,
    genero varchar(100) not null,
    pais_residencia varchar(100) not null,
    email varchar(100) not null unique,
    contrasena varchar(255) not null,
    perfil varchar(100) not null,
    activo boolean not null,
    email_activo boolean not null,

    primary key(id)

);

create table eventos(

    id bigserial not null,
    nombre varchar(100) not null,
    ciudad varchar(100) not null,
    direccion varchar(100) not null,
    fecha_inicio_evento timestamp not null,
    fecha_final_evento timestamp not null,
    activo boolean not null,

    primary key(id)

);

CREATE TABLE tipo_boletas(

    id bigserial not null,
    perfil varchar(100) not null,
    cantidad int not null,
    costo int not null,
    activo boolean not null,
    evento_id bigserial not null,

    constraint fk_tipo_boletas_evento_id foreign key(evento_id) references eventos(id),

    primary key(id)
);


create table boletas(

    id bigserial not null,
    token varchar(255) not null unique,
    asiento varchar(100) not null,
    fecha_compra timestamp not null,
    fecha_vencimiento timestamp not null,

    activo boolean not null,
    estado varchar(100) not null,

    usuario_id bigserial not null,
    tipo_boleta_id bigserial not null,

    constraint fk_boletas_usuario_id foreign key(usuario_id) references usuarios(id),
    constraint fk_boletas_tipo_boleta_id foreign key(tipo_boleta_id) references tipo_boletas(id),

    primary key(id)

);
