-- Crear tabla tipo_empleado
CREATE TABLE IF NOT EXISTS tipo_empleado (
    id_tipo_empleado SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT NOW(),
    creado_por VARCHAR(100)
);

-- Agregar columna id_tipo_empleado a la tabla empleado
ALTER TABLE empleado ADD COLUMN IF NOT EXISTS id_tipo_empleado INTEGER;

-- Crear la clave foránea
ALTER TABLE empleado ADD CONSTRAINT fk_empleado_tipo_empleado 
    FOREIGN KEY (id_tipo_empleado) REFERENCES tipo_empleado(id_tipo_empleado);

-- Insertar algunos tipos de empleado por defecto
INSERT INTO tipo_empleado (nombre, descripcion, activo, creado_por) VALUES 
('Médico', 'Personal médico que atiende pacientes', true, 'system'),
('Enfermero', 'Personal de enfermería', true, 'system'),
('Administrativo', 'Personal administrativo', true, 'system'),
('Técnico', 'Personal técnico', true, 'system')
ON CONFLICT DO NOTHING;







