package common.utils.token;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成Token的工具类
 * @author duan
 * @since 2020-5-25 14:01:41
 */
public class TokenTools {
    /**
     * 生成Token放入Session
     * @param tokenServerkey
     */
    public static String createToken(String tokenServerkey){
        //        request.getSession().setAttribute(tokenServerkey, token);
        return TokenProccessor.getInstance().makeToken();
    }

    /**
     * 移除token
     * @param request
     * @param tokenServerkey
     */
    public static void removeToken(HttpServletRequest request, String tokenServerkey){
        request.getSession().removeAttribute(tokenServerkey);
    }

    /**
     * 判断请求参数中的token是否和session中的一致
     * @param request
     * @param tokenClientkey
     * @param tokenServerkey
     * @return
     */
    public static boolean judgeTokenIsEqual(HttpServletRequest request,String tokenClientkey, String tokenServerkey){
        String token_client = request.getHeader(tokenClientkey);
        String token_server = (String)request.getSession().getAttribute(tokenServerkey);
        if(StringUtils.isEmpty(token_server)){
            return false;
        }
        return token_server.equals(token_client);
    }
}
