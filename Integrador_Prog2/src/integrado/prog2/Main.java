package integrado.prog2;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProductoDAO productoDAO = new ProductoDAO(); // ¡Instanciamos nuestro nuevo DAO!
        int opcion = -1;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios (En desarrollo...)");
            System.out.println("4. Pedidos (En desarrollo...)");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiamos el salto de línea

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner, categoriaDAO);
                        break;
                    case 2:
                        // Le pasamos los dos DAOs, porque para crear un producto necesitamos ver las categorías
                        menuProductos(scanner, productoDAO, categoriaDAO);
                        break;
                    case 3:
                    case 4:
                        System.out.println("¡Paciencia! Esta sección la armamos pronto.");
                        break;
                    case 0:
                        System.out.println("¡Saliendo del sistema! Nos vemos.");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Ingresá un número del 0 al 4.");
                }
            } else {
                System.out.println("Error: Debes ingresar un número.");
                scanner.next(); // Descartamos la entrada mala
            }
        } while (opcion != 0);

        scanner.close();
    }

    // --- SUBMENÚ DE CATEGORÍAS (Queda igual que antes) ---
    private static void menuCategorias(Scanner scanner, CategoriaDAO dao) {
        int opcionCat = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionCat = scanner.nextInt();
                scanner.nextLine();

                switch (opcionCat) {
                    case 1:
                        System.out.println("\n--- Lista de Categorías ---");
                        List<Categoria> lista = dao.listar();
                        if (lista.isEmpty()) System.out.println("No hay categorías cargadas.");
                        else for (Categoria c : lista) System.out.println(c);
                        break;
                    case 2:
                        System.out.print("Ingrese nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese descripción: ");
                        String desc = scanner.nextLine();
                        if (!nombre.trim().isEmpty()) {
                            dao.crear(new Categoria(nombre, desc));
                        } else {
                            System.out.println("El nombre no puede estar vacío.");
                        }
                        break;
                    case 3:
                        System.out.print("ID de la categoría a editar: ");
                        if (scanner.hasNextLong()) {
                            Long idMod = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Nuevo nombre: ");
                            String nuevoNombre = scanner.nextLine();
                            System.out.print("Nueva descripción: ");
                            String nuevaDesc = scanner.nextLine();
                            Categoria catMod = new Categoria(nuevoNombre, nuevaDesc);
                            catMod.setId(idMod);
                            dao.modificar(catMod);
                        } else {
                            System.out.println("ID inválido.");
                            scanner.next();
                        }
                        break;
                    case 4:
                        System.out.print("ID de la categoría a eliminar: ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("¿Seguro? (S/N): ");
                            if (scanner.nextLine().equalsIgnoreCase("S")) dao.eliminar(idElim);
                            else System.out.println("Cancelado.");
                        } else {
                            System.out.println("ID inválido.");
                            scanner.next();
                        }
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } else {
                System.out.println("Error: Debes ingresar un número.");
                scanner.next();
            }
        } while (opcionCat != 0);
    }

    // --- NUEVO SUBMENÚ DE PRODUCTOS ---
    private static void menuProductos(Scanner scanner, ProductoDAO prodDao, CategoriaDAO catDao) {
        int opcionProd = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionProd = scanner.nextInt();
                scanner.nextLine();

                switch (opcionProd) {
                    case 1: // Listar
                        System.out.println("\n--- Lista de Productos ---");
                        List<Producto> lista = prodDao.listar();
                        if (lista.isEmpty()) System.out.println("No hay productos cargados.");
                        else for (Producto p : lista) System.out.println(p);
                        break;

                    case 2: // Crear
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        if (nombre.trim().isEmpty()) {
                            System.out.println("El nombre no puede estar vacío.");
                            break;
                        }

                        System.out.print("Precio: $");
                        Double precio = scanner.nextDouble();
                        System.out.print("Stock inicial: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer

                        // Validación exigida por el TPI
                        if (precio < 0 || stock < 0) {
                            System.out.println("Error: El precio y el stock no pueden ser negativos.");
                            break;
                        }

                        System.out.print("Descripción: ");
                        String desc = scanner.nextLine();

                        // Mostramos las categorías para que el usuario sepa qué ID elegir
                        System.out.println("\nCategorías disponibles:");
                        for (Categoria c : catDao.listar()) {
                            System.out.println("ID: " + c.getId() + " - " + c.getNombre());
                        }
                        System.out.print("Ingrese el ID de la Categoría: ");
                        Long idCat = scanner.nextLong();
                        scanner.nextLine(); // Limpiar buffer

                        // Armamos el objeto
                        Producto nuevoProd = new Producto();
                        nuevoProd.setNombre(nombre);
                        nuevoProd.setPrecio(precio);
                        nuevoProd.setStock(stock);
                        nuevoProd.setDescripcion(desc);
                        nuevoProd.setImagen("sin-imagen.png"); // Valor por defecto
                        nuevoProd.setDisponible(true); // Disponible por defecto

                        // Enlazamos la categoría elegida
                        Categoria catAsignada = new Categoria();
                        catAsignada.setId(idCat);
                        nuevoProd.setCategoria(catAsignada);

                        prodDao.crear(nuevoProd);
                        break;

                    case 3: // Editar
                        System.out.print("Ingrese el ID del producto a editar: ");
                        if (scanner.hasNextLong()) {
                            Long idMod = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Nuevo nombre: ");
                            String nNombre = scanner.nextLine();
                            System.out.print("Nuevo precio: $");
                            Double nPrecio = scanner.nextDouble();
                            System.out.print("Nuevo stock: ");
                            int nStock = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Nuevo ID de Categoría: ");
                            Long nIdCat = scanner.nextLong();
                            scanner.nextLine();

                            if (nPrecio < 0 || nStock < 0) {
                                System.out.println("Error: Precio o stock negativos.");
                                break;
                            }

                            Producto prodMod = new Producto();
                            prodMod.setId(idMod);
                            prodMod.setNombre(nNombre);
                            prodMod.setPrecio(nPrecio);
                            prodMod.setStock(nStock);
                            prodMod.setDescripcion(""); // Para simplificar en la prueba
                            prodMod.setImagen("sin-imagen.png");
                            prodMod.setDisponible(true);

                            Categoria cMod = new Categoria();
                            cMod.setId(nIdCat);
                            prodMod.setCategoria(cMod);

                            prodDao.modificar(prodMod);
                        } else {
                            System.out.println("ID inválido.");
                            scanner.next();
                        }
                        break;

                    case 4: // Eliminar
                        System.out.print("ID del producto a eliminar: ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("¿Seguro? (S/N): ");
                            if (scanner.nextLine().equalsIgnoreCase("S")) prodDao.eliminar(idElim);
                            else System.out.println("Cancelado.");
                        } else {
                            System.out.println("ID inválido.");
                            scanner.next();
                        }
                        break;

                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } else {
                System.out.println("Error: Debes ingresar un número.");
                scanner.next();
            }
        } while (opcionProd != 0);
    }
}