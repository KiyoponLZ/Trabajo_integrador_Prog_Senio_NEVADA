package integrado.prog2;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.entities.Categoria;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        int opcion = -1;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos (En desarrollo...)");
            System.out.println("3. Usuarios (En desarrollo...)");
            System.out.println("4. Pedidos (En desarrollo...)");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            // Validamos que no se rompa si el usuario ingresa una letra en vez de número
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiamos el salto de línea

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner, categoriaDAO);
                        break;
                    case 2:
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
                    case 1: // HU-CAT-01 [cite: 279]
                        System.out.println("\n--- Lista de Categorías ---");
                        List<Categoria> lista = dao.listar();
                        if (lista.isEmpty()) {
                            System.out.println("No hay categorías cargadas.");
                        } else {
                            for (Categoria c : lista) {
                                System.out.println(c);
                            }
                        }
                        break;
                    case 2: // HU-CAT-02 [cite: 285]
                        System.out.print("Ingrese nombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese descripción: ");
                        String desc = scanner.nextLine();

                        if (!nombre.trim().isEmpty()) {
                            Categoria nueva = new Categoria(nombre, desc);
                            dao.crear(nueva);
                        } else {
                            System.out.println("El nombre no puede estar vacío.");
                        }
                        break;
                    case 3: // HU-CAT-03 [cite: 291]
                        System.out.print("Ingrese el ID de la categoría a editar: ");
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
                    case 4: // HU-CAT-04 [cite: 297]
                        System.out.print("Ingrese el ID de la categoría a eliminar: ");
                        if (scanner.hasNextLong()) {
                            Long idElim = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("¿Seguro que desea eliminarla? (S/N): ");
                            String confirmacion = scanner.nextLine();
                            if (confirmacion.equalsIgnoreCase("S")) {
                                dao.eliminar(idElim);
                            } else {
                                System.out.println("Operación cancelada.");
                            }
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
}