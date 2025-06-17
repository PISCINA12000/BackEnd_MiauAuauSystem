package miau.auau.amigosdequatropatas.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import miau.auau.amigosdequatropatas.security.JWTTokenProvider;

import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String token = req.getHeader("Authorization");
        String method = req.getMethod();
        String path = req.getRequestURI();

        // Rotas públicas
        if (isPublicRoute(method, path)) {
            chain.doFilter(request, response);
            return;
        }

        // Verificação do token para rotas protegidas
        if (token == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Não autorizado: token não encontrado");
            return;
        }

        if (!JWTTokenProvider.verifyToken(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Não autorizado: token inválido");
            return;
        }

        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
        String nivel = claims.get("privilegio").toString();

        // Rotas específicas
        if (isAdocaoRoute(method, path)) {
            if (nivel.equals("C") || nivel.equals("A")) {
                chain.doFilter(request, response);
            } else {
                forbidden(res);
            }
        } else if (isUsuarioRoute(method, path)) {
            if (method.equals("PUT") && !nivel.equals("C") && !nivel.equals("A")) {
                forbidden(res);
            } else if (method.equals("GET") && path.contains("/buscar-id") && !nivel.equals("C") && !nivel.equals("A")) {
                forbidden(res);
            } else {
                chain.doFilter(request, response);
            }
        }
        else if (isAnimalRoute(method, path)) {
            chain.doFilter(request, response);
        } else if (path.startsWith("/apis/")) {
            // Qualquer outra rota começando com /apis/ é restrita a "A"
            if (nivel.equals("A")) {
                chain.doFilter(request, response);
            } else {
                forbidden(res);
            }
        }
    }

    private boolean isPublicRoute(String method, String path) {
        return (method.equals("GET") && (path.contains("/apis/animal/buscar-filtro")
                || path.contains("/apis/animal/buscar-cor")
                || path.contains("/apis/animal/buscar-raca")
                || path.contains("/apis/usuario/buscar-cpf")
                || path.contains("/apis/usuario/buscar-email")))
                || (method.equals("POST") && path.contains("/apis/usuario/gravar"));
    }

    private boolean isAdocaoRoute(String method, String path) {
        return path.startsWith("/apis/adocao") && (
                (method.equals("POST") && path.contains("/solicitar"))
                        || (method.equals("GET") && path.matches(".*/buscarAdocaoPeloUsuId/\\d+/[^/]+"))
                        || (method.equals("PUT") && path.contains("/atualizar"))
                        || (method.equals("GET") && path.matches(".*/buscar-id/\\d+"))
        );
    }

    private boolean isUsuarioRoute(String method, String path) {
        // Verifica se é PUT para /atualizar ou GET para /buscar-id
        return (path.startsWith("/apis/usuario") && method.equals("PUT") && path.contains("/atualizar")) ||
                (path.startsWith("/apis/usuario/buscar-id") && method.equals("GET"));
    }


    private boolean isAnimalRoute(String method, String path) {
        return path.startsWith("/apis/animal") && (
                path.contains("/buscar-filtro") || path.contains("/buscar-cor") || path.contains("/buscar-raca")
        );
    }

    private void forbidden(HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.getWriter().write("Acesso negado");
    }

}