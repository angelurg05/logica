package Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Logica {

    static int contG = 0;
    static boolean salida[][];
    static List<String> variables = new ArrayList<String>();
    static Stack<String> cifras = new Stack<String>();

    public StringBuilder ExpLogica(String CadLog) {
        StringBuilder resultado = new StringBuilder();
        String Elog = OperCaracter(CadLog);
        if (Elog != "0") {
            int n = variables.size(), nn = 0;
            String OPost = OrdenPost(Elog);//OrdenPostfijo
            String Cifras[] = OPost.split("\\s");
            boolean tabla[][] = llenado(n);
            for (int i = 0; i < Cifras.length; i++) {
                if (Cifras[i].matches("^\\!$") || Cifras[i].matches("^\\&$") || Cifras[i].matches("^\\#$") || Cifras[i].matches("^->$") || Cifras[i].matches("^<->$")) {
                    nn++;
                }
            }
            salida = new boolean[tabla.length][nn];
            for (int i = 0; i < Cifras.length; i++) {
                if (Cifras[i].matches("^[A-Z]{1}$")) {
                    cifras.push(Cifras[i]);
                } else if (Cifras[i].matches("^\\!$")) {//Not
                    String p1 = cifras.pop();
                    NOT(p1, tabla);
                    cifras.push("" + contG);
                    contG++;
                } else if (Cifras[i].matches("^\\&$")) {//AND
                    String p2 = cifras.pop();
                    String p1 = cifras.pop();
                    AND(p1, p2, tabla);
                    cifras.push("" + contG);
                    contG++;
                } else if (Cifras[i].matches("^\\#$")) {//OR
                    String p2 = cifras.pop();
                    String p1 = cifras.pop();
                    OR(p1, p2, tabla);
                    cifras.push("" + contG);
                    contG++;
                } else if (Cifras[i].matches("^->$")) {//Condicional
                    String p2 = cifras.pop();
                    String p1 = cifras.pop();
                    Implicacion(p1, p2, tabla);
                    cifras.push("" + contG);
                    contG++;
                } else if (Cifras[i].matches("^<->$")) {//Bicondicional
                    String p2 = cifras.pop();
                    String p1 = cifras.pop();
                    DobleImplicacion(p1, p2, tabla);
                    cifras.push("" + contG);
                    contG++;
                } else {
                    System.out.println("No se puede resolver, revise la expresión");
                }
            }

            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < tabla.length; j++) {
                    tabla[j][n] = salida[j][salida[0].length - 1];
                }
            }
            System.out.println("Resultado: " + Resultado(tabla));
            resultado.append("Tipo de Resultado: "+Resultado(tabla)).append("\n");
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < tabla.length; j++) {
                    System.out.println("Salida " + (j + 1) + " : " + tabla[j][n]);
                       resultado.append("Salida " + (j + 1) + " : " + tabla[j][n]).append("\n");
                }
            }
            contG=0;
            variables.clear();
            cifras.clear();
        } else {
            System.out.println("No se puede resolver, revise la expresión");
        }
        return resultado;
    }

    public static boolean[][] llenado(int n) {
        //M=filas N=columnas
        int m = (int) Math.pow(2, n);
        int verdad = m / 2, cp = 0, cs = 0;
        boolean tabla[][] = new boolean[m][n + 1];
        for (int i = 0; i < tabla[0].length - 1; i++) {//Columna
            for (int j = 0; j < tabla.length; j++) {//Fila
                if (cp < verdad) {
                    tabla[j][i] = true;
                    cp++;
                } else if (cs < verdad) {
                    tabla[j][i] = false;
                    cs++;
                } else {
                    tabla[j][i] = true;
                    cp = 1;
                    cs = 0;
                }
            }
            cp = 0;
            cs = 0;
            verdad = verdad / 2;
        }
        return tabla;
    }

    public static String OperCaracter(String ExpL) {
        String valor = "";
        String caracter[] = ExpL.split("");
        int entrada = 0, cierre = 0;
        for (int i = 0; i < caracter.length; i++) {
            if (caracter[i].matches("^[\\<||\\-||>]$")) {
                valor += caracter[i];
            } else if (caracter[i].matches("^[A-Z]{1}$")) {
                valor = valor + " " + caracter[i] + " ";
                if (variables.isEmpty()) {
                    variables.add(caracter[i]);
                } else {
                    boolean ex = variables.contains(caracter[i]);
                    if (!ex) {
                        variables.add(caracter[i]);
                    }
                }
            } else {
                if (caracter[i].matches("^\\($")) {
                    valor = valor + " " + caracter[i] + " ";
                    entrada++;
                } else if (caracter[i].matches("^\\)$")) {
                    valor = valor + " " + caracter[i] + " ";
                    cierre++;
                } else {
                    valor = valor + " " + caracter[i] + " ";
                }
            }
        }
        if (entrada == cierre) {
            valor = valor.replaceAll("\\s+", " ");
            valor = valor.trim();
            return valor;
        } else {
            return "0";
        }
    }

    public static boolean[][] NOT(String var, boolean variable[][]) {
        boolean ex = variables.contains(var);
        int p = 0;
        if (ex) {
            p = variables.indexOf(var);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (!variable[j][p] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        } else {
            p = Integer.parseInt(var);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (!salida[j][p] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        }
        return variable;
    }

    public static boolean[][] AND(String p1, String p2, boolean variable[][]) {
        boolean ex1 = variables.contains(p1);
        boolean ex2 = variables.contains(p2);
        int v1 = 0, v2 = 0;
        if (ex1 && ex2) {
            v1 = variables.indexOf(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] && variable[j][v2]) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == false && ex2 == true) {
            v1 = Integer.parseInt(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] && variable[j][v2]) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == true && ex2 == false) {
            v1 = variables.indexOf(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] && salida[j][v2]) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else {
            v1 = Integer.parseInt(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] && salida[j][v2]) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        }
        return variable;
    }

    public static boolean[][] OR(String p1, String p2, boolean variable[][]) {
        boolean ex1 = variables.contains(p1);
        boolean ex2 = variables.contains(p2);
        int v1 = 0, v2 = 0;
        if (ex1 && ex2) {
            v1 = variables.indexOf(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        } else if (ex1 == false && ex2 == true) {
            v1 = Integer.parseInt(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        } else if (ex1 == true && ex2 == false) {
            v1 = variables.indexOf(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        } else {
            v1 = Integer.parseInt(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = false;
                    } else {
                        salida[j][contG] = true;
                    }
                }
            }
        }
        return variable;
    }

    public static boolean[][] Implicacion(String p1, String p2, boolean variable[][]) {
        boolean ex1 = variables.contains(p1);
        boolean ex2 = variables.contains(p2);
        int v1 = 0, v2 = 0;
        if (ex1 && ex2) {
            v1 = variables.indexOf(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == true && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == false && ex2 == true) {
            v1 = Integer.parseInt(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == true && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == true && ex2 == false) {
            v1 = variables.indexOf(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == true && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else {
            v1 = Integer.parseInt(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == true && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        }
        return variable;
    }

    public static boolean[][] DobleImplicacion(String p1, String p2, boolean variable[][]) {
        boolean ex1 = variables.contains(p1);
        boolean ex2 = variables.contains(p2);
        int v1 = 0, v2 = 0;
        if (ex1 && ex2) {
            v1 = variables.indexOf(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == true && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == false && ex2 == true) {
            v1 = Integer.parseInt(p1);
            v2 = variables.indexOf(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == true && variable[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && variable[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else if (ex1 == true && ex2 == false) {
            v1 = variables.indexOf(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (variable[j][v1] == true && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (variable[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        } else {
            v1 = Integer.parseInt(p1);
            v2 = Integer.parseInt(p2);
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < variable.length; j++) {
                    if (salida[j][v1] == true && salida[j][v2] == true) {
                        salida[j][contG] = true;
                    } else if (salida[j][v1] == false && salida[j][v2] == false) {
                        salida[j][contG] = true;
                    } else {
                        salida[j][contG] = false;
                    }
                }
            }
        }
        return variable;
    }

    public static String OrdenPost(String exp) {
        Stack<String> operacion = new Stack<String>();
        Stack<String> operadores = new Stack<String>();
        Stack<String> Aux = new Stack<String>();
        String Exp[] = exp.split("\\s");
        String Post = "";
        int Presedencia = 0;
        //  4   3     2     1
        //  (   !     &#   -><->
        /*      1. Precedencia igual se cambian
                2. Precedencia menor se agrega a la pila
                3. Presedencia mayor se sacan valores de la pila*/
        for (int i = 0; i < Exp.length; i++) {
            if (Exp[i].matches("^[A-Z]{1}$")) {//Variable
                operacion.push(Exp[i]);
            } else if (Exp[i].matches("^\\($")) {//Parentesis
                if (Presedencia == 4) {
                    operadores.push(Exp[i]);
                } else if (Presedencia < 4) {
                    operadores.push(Exp[i]);
                } else {
                    System.out.println("Revisa la expresión 1");
                }
                Presedencia = 4;
            } else if (Exp[i].matches("^\\!$")) {//Negacion
                if (Presedencia == 3) {
                    operacion.push(operadores.pop());
                    operadores.push(Exp[i]);
                } else if (Presedencia < 3) {
                    operadores.push(Exp[i]);
                } else if (Presedencia == 4) {
                    operadores.push(Exp[i]);
                } else {
                    System.out.println("Revisa la expresión 2");
                }
                Presedencia = 3;
            } else if (Exp[i].matches("^[\\&||\\#]$")) {//AND y OR
                if (Presedencia == 2) {
                    operacion.push(operadores.pop());
                    operadores.push(Exp[i]);
                } else if (Presedencia < 2) {
                    operadores.push(Exp[i]);
                } else if (Presedencia > 2 && Presedencia != 4) {
                    for (int j = operadores.size(); j > 0; j--) {
                        String operador = operadores.pop();
                        if (operador.matches("^\\($")) {
                            operadores.push(operador);
                            j = 0;
                        } else {
                            operacion.push(operador);
                        }
                    }
                    operadores.push(Exp[i]);
                } else if (Presedencia == 4) {
                    operadores.push(Exp[i]);
                } else {
                    System.out.println("Revisa la expresión 3");
                }
                Presedencia = 2;
            } else if (Exp[i].matches("^->$") || Exp[i].matches("^<->$")) {//Condicional y Bicondicional
                if (Presedencia == 1) {
                    operacion.push(operadores.pop());
                    operadores.push(Exp[i]);
                } else if (Presedencia < 1) {
                    operadores.push(Exp[i]);
                } else if (Presedencia > 1 && Presedencia != 4) {
                    for (int j = operadores.size(); j > 0; j--) {
                        String operador = operadores.pop();
                        if (operador.matches("^\\($")) {
                            operadores.push(operador);
                            j = 0;
                        } else {
                            operacion.push(operador);
                        }
                    }
                    operadores.push(Exp[i]);
                } else if (Presedencia == 4) {
                    operadores.push(Exp[i]);
                } else {
                    System.out.println("Revisa la expresión 4 " + i + " " + Presedencia);
                }
                Presedencia = 1;
            } else if (Exp[i].matches("^\\)$")) {
                for (int j = operadores.size(); j > 0; j--) {
                    String operador = operadores.pop();
                    if (operador.matches("^\\($")) {
                        if (!operadores.empty()) {
                            String OperAux = operadores.pop();
                            if (OperAux.matches("^\\($")) {
                                Presedencia = 4;
                                operadores.push(OperAux);
                            } else if (OperAux.matches("^\\!$")) {
                                Presedencia = 3;
                                operadores.push(OperAux);
                            } else if (OperAux.matches("^[\\&||\\#]$")) {
                                Presedencia = 2;
                                operadores.push(OperAux);
                            } else if (OperAux.matches("^->$") || OperAux.matches("^<->$")) {
                                Presedencia = 1;
                                operadores.push(OperAux);
                            } else {
                                System.out.println("Revisa la expresión 5");
                            }
                            j = 0;
                        } else {
                            Presedencia = 0;
                            j = 0;
                        }
                    } else {
                        operacion.push(operador);
                    }
                }
            } else {
                System.out.println(i + " Revisa la expresión 6 ");
            }
        }
        if (!operadores.empty()) {
            for (int i = operadores.size(); i > 0; i--) {
                operacion.push(operadores.pop());
            }
        }
        for (int i = operacion.size(); i > 0; i--) {
            Aux.push(operacion.pop());
        }
        for (int i = Aux.size(); i > 0; i--) {
            Post = Post + Aux.pop() + " ";
        }
        return Post;
    }

    public static String Resultado(boolean TablaC[][]) {
        int conVerdad = 0, conFalso = 0, tam = TablaC[0].length - 1;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < TablaC.length; j++) {
                if (TablaC[j][tam]) {
                    conVerdad++;
                } else {
                    conFalso++;
                }
            }
        }
        if (conVerdad == TablaC.length) {
            return "Tautología";
        } else if (conFalso == TablaC.length) {
            return "Contradicción";
        } else {
            return "Contingencia";
        }
    }
}
