package flagak.com.e_commerce.utils;

import flagak.com.e_commerce.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth/user/login",
            "/auth/user/signup",
            "/auth/vendor/login",
            "/auth/vendor/signup",
            "/shop"
    );


    @Autowired
    public JwtAuthFilter( JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        System.out.println("Request Path: " + requestPath);
        if (EXCLUDED_PATHS.contains(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        Long id = null;
        String role = null;
        String jwtToken = null;

        System.out.println(authorizationHeader);
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);  // extract the token from "Bearer "
                System.out.println("Token received");

                id = jwtUtil.extractId(jwtToken);
                role = jwtUtil.extractRole(jwtToken);
                System.out.println("ID: " + id + ", Role: " + role);
            }
            else{
                System.out.println("Error: Authorization header is missing or invalid");
                throw new InvalidTokenException("Invalid token");
            }
            if(id == null || role == null) {
                System.out.println("Error: Invalid token - missing id or role");
                throw new InvalidTokenException("Invalid token");
            }
            if (!jwtUtil.validateToken(jwtToken, id)) {
                throw new InvalidTokenException("Invalid or expired token");

            }

            request.setAttribute("id", id);
            request.setAttribute("role", role);

            System.out.println("role: " + role);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role));
            Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        }catch (InvalidTokenException e){
            System.out.println("Custom Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
//            response.getWriter().write(e.getMessage());
//            response.getWriter().flush();
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
//            response.getWriter().write("Invalid token");
//            response.getWriter().flush();
        }

    }

}
