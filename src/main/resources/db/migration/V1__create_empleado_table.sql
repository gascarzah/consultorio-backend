-- Crear la secuencia para empleado si no existe
CREATE SEQUENCE IF NOT EXISTS empleado_id_seq;

-- Asegurarse de que la tabla empleado use la secuencia correctamente
ALTER TABLE empleado ALTER COLUMN id_empleado SET DEFAULT nextval('empleado_id_seq');
ALTER SEQUENCE empleado_id_seq OWNED BY empleado.id_empleado;

-- Reiniciar la secuencia al valor máximo actual
SELECT setval('empleado_id_seq', COALESCE((SELECT MAX(id_empleado) FROM empleado), 0) + 1, false); 