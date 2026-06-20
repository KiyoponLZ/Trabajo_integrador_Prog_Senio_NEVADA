package integrado.prog2;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int opcion = -1;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos (En desarrollo...)");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner, categoriaDAO);
                        break;
                    case 2:
                        menuProductos(scanner, productoDAO, categoriaDAO);
                        break;
                    case 3:
                        menuUsuarios(scanner, usuarioDAO);
                        break;
                    case 4:
                        System.out.println("¡Paciencia! Esta es la épica final y la armamos pronto.");
                        break;
                    case 0:
                        System.out.println("¡Saliendo del sistema! Nos vemos.");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Ingresá un número del 0 al 4.");
                }
            } else {
                System.out.println("Error: Debes ingresar un número.");
                scanner.next();
            }
        } while (opcion != 0);

        scanner.close();
    }

    // --- SUBMENÚ DE CATEGORÍAS ---
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
                        System.out.print("Ingrese nombre (o '0' para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) {
                            System.out.println("Operación cancelada.");
                            break;
                        }

                        System.out.print("Ingrese descripción: ");
                        String desc = scanner.nextLine();
                        if (!nombre.trim().isEmpty()) {
                            dao.crear(new Categoria(nombre, desc));
                        } else {
                            System.out.println("El nombre no puede estar vacío.");
                        }
                        break;
                    case 3:
                        System.out.print("ID de la categoría a editar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idMod = scanner.nextLong();
                            scanner.nextLine();
                            if (idMod == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

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
                        System.out.print("ID de la categoría a eliminar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            if (idElim == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

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

    // --- SUBMENÚ DE PRODUCTOS ---
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
                    case 1:
                        System.out.println("\n--- Lista de Productos ---");
                        List<Producto> lista = prodDao.listar();
                        if (lista.isEmpty()) System.out.println("No hay productos cargados.");
                        else for (Producto p : lista) System.out.println(p);
                        break;
                    case 2:
                        System.out.print("Nombre del producto (o '0' para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) {
                            System.out.println("Operación cancelada.");
                            break;
                        }
                        if (nombre.trim().isEmpty()) {
                            System.out.println("El nombre no puede estar vacío.");
                            break;
                        }
                        System.out.print("Precio: $");
                        Double precio = scanner.nextDouble();
                        System.out.print("Stock inicial: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        if (precio < 0 || stock < 0) {
                            System.out.println("Error: El precio y el stock no pueden ser negativos.");
                            break;
                        }

                        System.out.print("Descripción: ");
                        String desc = scanner.nextLine();

                        System.out.println("\nCategorías disponibles:");
                        for (Categoria c : catDao.listar()) {
                            System.out.println("ID: " + c.getId() + " - " + c.getNombre());
                        }
                        System.out.print("Ingrese el ID de la Categoría: ");
                        Long idCat = scanner.nextLong();
                        scanner.nextLine();

                        Producto nuevoProd = new Producto();
                        nuevoProd.setNombre(nombre);
                        nuevoProd.setPrecio(precio);
                        nuevoProd.setStock(stock);
                        nuevoProd.setDescripcion(desc);
                        nuevoProd.setImagen("sin-imagen.png");
                        nuevoProd.setDisponible(true);

                        Categoria catAsignada = new Categoria();
                        catAsignada.setId(idCat);
                        nuevoProd.setCategoria(catAsignada);

                        prodDao.crear(nuevoProd);
                        break;
                    case 3:
                        System.out.print("Ingrese el ID del producto a editar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idMod = scanner.nextLong();
                            scanner.nextLine();
                            if (idMod == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

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
                            prodMod.setDescripcion("");
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
                    case 4:
                        System.out.print("ID del producto a eliminar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            if (idElim == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

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

    // --- SUBMENÚ DE USUARIOS ---
    private static void menuUsuarios(Scanner scanner, UsuarioDAO usuDao) {
        int opcionUsu = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionUsu = scanner.nextInt();
                scanner.nextLine();

                switch (opcionUsu) {
                    case 1:
                        System.out.println("\n--- Lista de Usuarios ---");
                        List<Usuario> lista = usuDao.listar();
                        if (lista.isEmpty()) System.out.println("No hay usuarios cargados.");
                        else for (Usuario u : lista) System.out.println(u);
                        break;
                    case 2:
                        System.out.print("Nombre (o '0' para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) {
                            System.out.println("Operación cancelada.");
                            break;
                        }

                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Mail: ");
                        String mail = scanner.nextLine();
                        System.out.print("Celular: ");
                        String celular = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String pass = scanner.nextLine();

                        System.out.println("Seleccione Rol (1 = ADMIN, 2 = USUARIO): ");
                        int opcionRol = scanner.nextInt();
                        scanner.nextLine();

                        Rol rolAsignado = (opcionRol == 1) ? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevoUsu = new Usuario();
                        nuevoUsu.setNombre(nombre);
                        nuevoUsu.setApellido(apellido);
                        nuevoUsu.setMail(mail);
                        nuevoUsu.setCelular(celular);
                        nuevoUsu.setContrasena(pass);
                        nuevoUsu.setRol(rolAsignado);

                        usuDao.crear(nuevoUsu);
                        break;
                    case 3:
                        System.out.print("Ingrese el ID del usuario a editar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idMod = scanner.nextLong();
                            scanner.nextLine();
                            if (idMod == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

                            System.out.print("Nuevo nombre: ");
                            String nNombre = scanner.nextLine();
                            System.out.print("Nuevo apellido: ");
                            String nApellido = scanner.nextLine();
                            System.out.print("Nuevo mail: ");
                            String nMail = scanner.nextLine();
                            System.out.print("Nuevo celular: ");
                            String nCelular = scanner.nextLine();
                            System.out.print("Nueva contraseña: ");
                            String nPass = scanner.nextLine();
                            System.out.println("Nuevo Rol (1 = ADMIN, 2 = USUARIO): ");
                            int nOpcionRol = scanner.nextInt();
                            scanner.nextLine();

                            Rol nRolAsignado = (nOpcionRol == 1) ? Rol.ADMIN : Rol.USUARIO;

                            Usuario usuMod = new Usuario();
                            usuMod.setId(idMod);
                            usuMod.setNombre(nNombre);
                            usuMod.setApellido(nApellido);
                            usuMod.setMail(nMail);
                            usuMod.setCelular(nCelular);
                            usuMod.setContrasena(nPass);
                            usuMod.setRol(nRolAsignado);

                            usuDao.modificar(usuMod);
                        } else {
                            System.out.println("ID inválido.");
                            scanner.next();
                        }
                        break;
                    case 4:
                        System.out.print("ID del usuario a eliminar (o 0 para cancelar): ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            if (idElim == 0) {
                                System.out.println("Operación cancelada.");
                                break;
                            }

                            System.out.print("¿Seguro? (S/N): ");
                            if (scanner.nextLine().equalsIgnoreCase("S")) usuDao.eliminar(idElim);
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
        } while (opcionUsu != 0);
    }
}