package me.shrestho.minimalcms.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.JwtUtils;
import me.shrestho.minimalcms.utils.Utils;
import me.shrestho.minimalcms.utils.enums.TokenType;
import me.shrestho.minimalcms.utils.enums.UserRoles;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;


public class AuthInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils = new JwtUtils();
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         System.out.println("==============================");
         System.out.println("Chole Ashchi" + " " +  request.getHeader("JWT"));
         // Check If JWT Exists
         String accessToken = request.getHeader("JWT");
         if(accessToken!=null && !accessToken.isEmpty()){



            System.out.println("OK");
            try{
                DecodedJWT decodedJwt = jwtUtils.verifyTokenAndReturnDecoded(accessToken);

                // Check token
//                decodedJwt.get
                System.out.println("Decoded JWT" + " "  + decodedJwt);
                System.out.println("XXX" + decodedJwt.getSubject() + " "+ decodedJwt.getClaim("role"));

                // if token type is refresh, throw error with message
                if( !(TokenType.valueOf(decodedJwt.getSubject()) == TokenType.ACCESS)){
                    throw new Exception("Invalid Token Type");
                }

                // Parsing User from Jwt

                Map<String, Claim> payloadClaims = decodedJwt.getClaims();
                User user = new User();
                UUID id = UUID.fromString(payloadClaims.get("id").asString());
                user.setId(id);
                user.setName(payloadClaims.get("name").asString());
                user.setUsername(payloadClaims.get("username").asString());
                user.setEmail(payloadClaims.get("email").asString());
                user.setRole(UserRoles.valueOf(payloadClaims.get("role").asString()));


                System.out.println("USER = " + user);




//                if(UserRoles.valueOf(String.valueOf(decodedJwt.getClaim("role"))) == UserRoles.ADMIN ){
//                    System.out.println("ADMIN");
//                }else if(UserRoles.valueOf(String.valueOf(decodedJwt.getClaim("role"))) == UserRoles.USER){
//                    System.out.println("USER");
//                }

                // We are safe here
//                User user = new User();


//                response.ge

                return true; // like a next() in express

            } catch (Exception e){

                throw e;

            }

         }
         System.out.println("JWT Null Or Empty");

         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         throw new Exception("Unauthorized");

    }

     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
         System.out.println("==============================");
         System.out.println("Chole Jacchi");
         System.out.println("==============================");
    }

     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
         System.out.println("==============================");
         System.out.println("AfterComplition" + " " + request.getRequestURI());
         System.out.println("==============================");
    }
}
