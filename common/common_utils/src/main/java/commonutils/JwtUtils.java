package commonutils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author android
 */
public class JwtUtils {
    //设置token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;
    //秘钥
    public static final String APP_SECRET = "520android1314world886";

    //生成token字符串
    public static String getJwtToken(int id, String nickname){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("zk-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", nickname)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

        /**
         * 判断token是否存在与有效
         * @param jwtToken
         * @return
         */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

        /**
         * 判断token是否存在与有效
         */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

        /**
         * 根据token获取会员id
         * @param request
         * @return
         */
    public static int getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return 0;
        }
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken).getBody();
        }catch (ExpiredJwtException e){
            claims = e.getClaims();
        }
        return (int)claims.get("id");
    }
}
