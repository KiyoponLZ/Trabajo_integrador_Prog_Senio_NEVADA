package integrado.prog2;

import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ¡MAGIA! Ahora instanciamos los Servicios (Punto 5)
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService();

        int opcion = -1;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1: menuCategorias(scanner, categoriaService); break;
                    case 2: menuProductos(scanner, productoService, categoriaService); break;
                    case 3: menuUsuarios(scanner, usuarioService); break;
                    case 4: menuPedidos(scanner, pedidoService, productoService, usuarioService); break;
                    case 0: System.out.println("¡Saliendo del sistema! Nos vemos."); break;
                    default: System.out.println("Opción incorrecta. Ingresá un número del 0 al 4.");
                }
            } else {
                System.out.println("Error: Debes ingresar un número.");
                scanner.next();
            }
        } while (opcion != 0);

        scanner.close();
    }

    // --- SUBMENÚ DE CATEGORÍAS ---
    private static void menuCategorias(Scanner scanner, CategoriaService service) {
        int opcionCat = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionCat = scanner.nextInt();
                scanner.nextLine();

                switch (opcionCat) {
                    case 1:
                        List<Categoria> lista = service.listar();
                        if (lista.isEmpty()) System.out.println("No hay categorías cargadas.");
                        else for (Categoria c : lista) System.out.println(c);
                        break;
                    case 2:
                        System.out.print("Nombre (o 0 para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) break;
                        System.out.print("Descripción: ");
                        service.crear(new Categoria(nombre, scanner.nextLine()));
                        break;
                    case 3:
                        System.out.print("ID a editar (0 para cancelar): ");
                        Long idMod = scanner.nextLong(); scanner.nextLine();
                        if (idMod == 0) break;
                        System.out.print("Nuevo nombre: "); String nNombre = scanner.nextLine();
                        System.out.print("Nueva descripción: "); String nDesc = scanner.nextLine();
                        Categoria catMod = new Categoria(nNombre, nDesc);
                        catMod.setId(idMod);
                        service.modificar(catMod);
                        break;
                    case 4:
                        System.out.print("ID a eliminar (0 para cancelar): ");
                        Long idElim = scanner.nextLong(); scanner.nextLine();
                        if (idElim == 0) break;
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) service.eliminar(idElim);
                        break;
                    case 0: break;
                }
            } else {
                System.out.println("Error: Ingresá un número.");
                scanner.next();
            }
        } while (opcionCat != 0);
    }

    // --- SUBMENÚ DE PRODUCTOS ---
    private static void menuProductos(Scanner scanner, ProductoService prodService, CategoriaService catService) {
        int opcionProd = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionProd = scanner.nextInt();
                scanner.nextLine();

                switch (opcionProd) {
                    case 1:
                        List<Producto> lista = prodService.listar();
                        if (lista.isEmpty()) System.out.println("No hay productos.");
                        else for (Producto p : lista) System.out.println(p);
                        break;
                    case 2:
                        System.out.print("Nombre (0 para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) break;

                        System.out.print("Precio: $"); Double precio = scanner.nextDouble();
                        System.out.print("Stock: "); int stock = scanner.nextInt(); scanner.nextLine();
                        System.out.print("Descripción: "); String desc = scanner.nextLine();
                        System.out.print("ID Categoría: "); Long idCat = scanner.nextLong(); scanner.nextLine();

                        Producto p = new Producto();
                        p.setNombre(nombre); p.setPrecio(precio); p.setStock(stock); p.setDescripcion(desc);
                        p.setImagen("sin-imagen.png"); p.setDisponible(true);
                        Categoria c = new Categoria(); c.setId(idCat); p.setCategoria(c);
                        prodService.crear(p);
                        break;
                    case 3:
                        System.out.print("ID a editar (0 para cancelar): ");
                        Long idMod = scanner.nextLong(); scanner.nextLine();
                        if (idMod == 0) break;
                        System.out.print("Nuevo Nombre: "); String nNombre = scanner.nextLine();
                        System.out.print("Nuevo Precio: $"); Double nPrecio = scanner.nextDouble();
                        System.out.print("Nuevo Stock: "); int nStock = scanner.nextInt(); scanner.nextLine();
                        System.out.print("Nuevo ID Categoría: "); Long nIdCat = scanner.nextLong(); scanner.nextLine();

                        Producto pMod = new Producto(); pMod.setId(idMod); pMod.setNombre(nNombre);
                        pMod.setPrecio(nPrecio); pMod.setStock(nStock); pMod.setDescripcion("");
                        pMod.setImagen("sin-imagen.png"); pMod.setDisponible(true);
                        Categoria cMod = new Categoria(); cMod.setId(nIdCat); pMod.setCategoria(cMod);
                        prodService.modificar(pMod);
                        break;
                    case 4:
                        System.out.print("ID a eliminar (0 para cancelar): ");
                        Long idElim = scanner.nextLong(); scanner.nextLine();
                        if (idElim == 0) break;
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) prodService.eliminar(idElim);
                        break;
                    case 0: break;
                }
            } else {
                System.out.println("Error: Ingresá un número.");
                scanner.next();
            }
        } while (opcionProd != 0);
    }

    // --- SUBMENÚ DE USUARIOS ---
    private static void menuUsuarios(Scanner scanner, UsuarioService usuService) {
        int opcionUsu = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionUsu = scanner.nextInt();
                scanner.nextLine();

                switch (opcionUsu) {
                    case 1:
                        List<Usuario> lista = usuService.listar();
                        if (lista.isEmpty()) System.out.println("No hay usuarios.");
                        else for (Usuario u : lista) System.out.println(u);
                        break;
                    case 2:
                        System.out.print("Nombre (0 para cancelar): ");
                        String nombre = scanner.nextLine();
                        if (nombre.equals("0")) break;
                        System.out.print("Apellido: "); String apellido = scanner.nextLine();
                        System.out.print("Mail: "); String mail = scanner.nextLine();
                        System.out.print("Celular: "); String celular = scanner.nextLine();
                        System.out.print("Contraseña: "); String pass = scanner.nextLine();
                        System.out.print("Rol (1 = ADMIN, 2 = USUARIO): "); int opRol = scanner.nextInt(); scanner.nextLine();

                        Usuario u = new Usuario(); u.setNombre(nombre); u.setApellido(apellido);
                        u.setMail(mail); u.setCelular(celular); u.setContrasena(pass);
                        u.setRol((opRol == 1) ? Rol.ADMIN : Rol.USUARIO);
                        usuService.crear(u);
                        break;
                    case 3:
                        System.out.print("ID a editar (0 para cancelar): ");
                        Long idMod = scanner.nextLong(); scanner.nextLine();
                        if (idMod == 0) break;
                        System.out.print("Nuevo Nombre: "); String nNombre = scanner.nextLine();
                        System.out.print("Nuevo Apellido: "); String nApellido = scanner.nextLine();
                        System.out.print("Nuevo Mail: "); String nMail = scanner.nextLine();
                        System.out.print("Nuevo Celular: "); String nCelular = scanner.nextLine();
                        System.out.print("Nueva Contraseña: "); String nPass = scanner.nextLine();
                        System.out.print("Nuevo Rol (1 = ADMIN, 2 = USUARIO): "); int nOpRol = scanner.nextInt(); scanner.nextLine();

                        Usuario uMod = new Usuario(); uMod.setId(idMod); uMod.setNombre(nNombre);
                        uMod.setApellido(nApellido); uMod.setMail(nMail); uMod.setCelular(nCelular);
                        uMod.setContrasena(nPass); uMod.setRol((nOpRol == 1) ? Rol.ADMIN : Rol.USUARIO);
                        usuService.modificar(uMod);
                        break;
                    case 4:
                        System.out.print("ID a eliminar (0 para cancelar): ");
                        Long idElim = scanner.nextLong(); scanner.nextLine();
                        if (idElim == 0) break;
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) usuService.eliminar(idElim);
                        break;
                    case 0: break;
                }
            } else {
                System.out.println("Error: Ingresá un número.");
                scanner.next();
            }
        } while (opcionUsu != 0);
    }

    // --- NUEVO SUBMENÚ DE PEDIDOS (CARRITO) ---
    private static void menuPedidos(Scanner scanner, PedidoService pedService, ProductoService prodService, UsuarioService usuService) {
        int opcionPed = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Nuevo Pedido (CARRITO)");
            System.out.println("3. Cambiar Estado de Pedido");
            System.out.println("4. Eliminar Pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            if (scanner.hasNextInt()) {
                opcionPed = scanner.nextInt();
                scanner.nextLine();

                switch (opcionPed) {
                    case 1:
                        List<Pedido> lista = pedService.listar();
                        if (lista.isEmpty()) System.out.println("No hay pedidos registrados.");
                        else for (Pedido p : lista) System.out.println("Pedido ID: " + p.getId() + " - Total: $" + p.getTotal() + " - Estado: " + p.getEstado());
                        break;
                    case 2:
                        System.out.println("\n--- NUEVO PEDIDO ---");

                        // Mostramos los usuarios disponibles para que el operador elija
                        List<Usuario> usuariosDisponibles = usuService.listar();
                        if (usuariosDisponibles.isEmpty()) {
                            System.out.println("No hay usuarios registrados. Creá uno primero.");
                            break;
                        }
                        System.out.println("Usuarios disponibles:");
                        for (Usuario us : usuariosDisponibles) {
                            System.out.println("  [" + us.getId() + "] " + us.getNombre() + " " + us.getApellido() + " (" + us.getMail() + ")");
                        }

                        System.out.print("Ingrese ID del Usuario (0 para cancelar): ");
                        Long idUsu = scanner.nextLong(); scanner.nextLine();
                        if (idUsu == 0) break;

                        try {
                            // Validamos que el usuario exista usando el servicio (Puntos 3 y 6)
                            Usuario cliente = usuService.buscarPorId(idUsu);
                            System.out.println("✔ Cliente seleccionado: " + cliente.getNombre() + " " + cliente.getApellido());

                            Pedido nuevoPedido = new Pedido();
                            nuevoPedido.setUsuario(cliente);
                            nuevoPedido.setEstado(Estado.PENDIENTE);

                            boolean agregando = true;
                            while (agregando) {
                                // Mostramos los productos disponibles en cada vuelta del carrito
                                List<Producto> productosDisponibles = prodService.listar();
                                if (productosDisponibles.isEmpty()) {
                                    System.out.println("No hay productos disponibles.");
                                    break;
                                }
                                System.out.println("\nProductos disponibles:");
                                for (Producto prod : productosDisponibles) {
                                    System.out.println("  [" + prod.getId() + "] " + prod.getNombre() + " - $" + prod.getPrecio() + " (Stock: " + prod.getStock() + ")");
                                }

                                System.out.print("Ingrese ID del Producto a agregar (o 0 para Finalizar Compra): ");
                                Long idProd = scanner.nextLong(); scanner.nextLine();

                                if (idProd == 0) {
                                    agregando = false;
                                } else {
                                    try {
                                        // Buscamos el producto, si no existe tira nuestra EntityNotFoundException
                                        Producto pAgregado = prodService.buscarPorId(idProd);

                                        System.out.print("Cantidad: ");
                                        int cant = scanner.nextInt(); scanner.nextLine();

                                        if (cant <= 0) {
                                            System.out.println("Error: La cantidad debe ser mayor a 0.");
                                        } else if (cant > pAgregado.getStock()) {
                                            System.out.println("Error: ¡No hay stock suficiente! Solo quedan " + pAgregado.getStock() + " unidades.");
                                        } else {
                                            nuevoPedido.addDetallePedido(cant, pAgregado.getPrecio(), pAgregado);
                                            System.out.println("Se agregaron " + cant + "x '" + pAgregado.getNombre() + "'. Total actual: $" + nuevoPedido.getTotal());
                                        }
                                    } catch (EntityNotFoundException ex) {
                                        System.out.println("Error: " + ex.getMessage());
                                    }
                                }
                            }

                            if (nuevoPedido.getDetalles().isEmpty()) {
                                System.out.println("Pedido cancelado (Carrito vacío).");
                            } else {
                                System.out.println("Seleccione Forma de Pago (1. TARJETA, 2. EFECTIVO, 3. TRANSFERENCIA): ");
                                int fp = scanner.nextInt(); scanner.nextLine();
                                if (fp == 1) nuevoPedido.setFormaPago(FormaPago.TARJETA);
                                else if (fp == 2) nuevoPedido.setFormaPago(FormaPago.EFECTIVO);
                                else nuevoPedido.setFormaPago(FormaPago.TRANSFERENCIA);

                                // El servicio se encarga de las últimas validaciones antes del DAO
                                pedService.crear(nuevoPedido);
                            }
                        } catch (EntityNotFoundException e) {
                            // Atrapamos el error si el usuario no existe
                            System.out.println("Error al iniciar el pedido: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.print("ID del Pedido a editar (0 para cancelar): ");
                        Long idMod = scanner.nextLong(); scanner.nextLine();
                        if (idMod == 0) break;

                        System.out.println("Nuevo Estado (1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO): ");
                        int est = scanner.nextInt(); scanner.nextLine();
                        Pedido pedMod = new Pedido();
                        pedMod.setId(idMod);
                        if (est == 1) pedMod.setEstado(Estado.PENDIENTE);
                        else if (est == 2) pedMod.setEstado(Estado.CONFIRMADO);
                        else if (est == 3) pedMod.setEstado(Estado.TERMINADO);
                        else pedMod.setEstado(Estado.CANCELADO);

                        pedService.modificar(pedMod);
                        break;
                    case 4:
                        System.out.print("ID a eliminar (0 para cancelar): ");
                        Long idElim = scanner.nextLong(); scanner.nextLine();
                        if (idElim == 0) break;
                        System.out.print("¿Seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) pedService.eliminar(idElim);
                        break;
                    case 0: break;
                }
            } else {
                System.out.println("Error: Ingresá un número.");
                scanner.next();
            }
        } while (opcionPed != 0);
    }
}