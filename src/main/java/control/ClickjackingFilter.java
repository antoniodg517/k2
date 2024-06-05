package control;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ClickjackingFilter", urlPatterns = "/*")
public class ClickjackingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inizializzazione del filtro (non è necessario in questo caso)
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        // Verifica se la richiesta è per una pagina JSP per evitare di aggiungere header indesiderati ad altre risorse (es. immagini, stili CSS, ecc.)
        if (request.getRequestURI().endsWith(".jsp")) {
            // Aggiungi gli header anti-clickjacking alla risposta
            response.setHeader("X-Frame-Options", "DENY");
            response.setHeader("Content-Security-Policy", "frame-ancestors 'none'");
        }

        // Passa la richiesta al prossimo filtro nella catena
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // Operazioni di chiusura del filtro (non è necessario in questo caso)
    }
}