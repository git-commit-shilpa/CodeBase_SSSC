package com.securecode.Web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.securecode.Controller.LoginController;
import com.securecode.Controller.SessionManager;
import com.securecode.DomainObject.MemberDomainObject;
import com.securecode.restService.Alert;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.owasp.encoder.Encode;

@Controller
public class WebController {


    private final LoginController loginController;

    public WebController(LoginController loginController) {
        this.loginController = loginController;
    }


	@CrossOrigin(origins = "*")
	@GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String login() {

        return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Login</h2>" +
			"  <form action=\"/login\" method=\"post\">" +
			"      <input name=\"username\" type=\"text\" placeholder=\"login\" value=\"\" />" +
			"      <input name=\"password\" type=\"password\" placeholder=\"password\" value=\"\" />" +
			"      <input type=\"submit\" value=\"login\" />" +
			"  </form>" +
			"</body>" +
			"</html>";
    }


	@CrossOrigin(origins = "*")
	@PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String login(HttpServletResponse response, String username, String password) {

		Alert alert = new Alert();

		MemberDomainObject member = this.loginController.Login(alert, username, password);

		if (member == null) {

			return 
				"<html>" +
				"<title></title>" +
				"<body>" +
				"  <h2>Login</h2>" +
				"  <div>Invalid Credentials</div>" +
				"  <form action=\"/login\" method=\"post\">" +
				"      <input name=\"username\" type=\"text\" placeholder=\"login\" value=\"\" />" +
				"      <input name=\"password\" type=\"password\" placeholder=\"password\" value=\"\" />" +
				"      <input type=\"submit\" value=\"login\" />" +
				"  </form>" +
				"</body>" +
				"</html>";

		}

		String sessionkey = SessionManager.GenerateNewSessionKey();
		SessionManager.SetSession(sessionkey, member.getMemberId());


		Cookie cookie = new Cookie("Session", sessionkey);
		response.addCookie(cookie);


		String welcomeContent = String.format("Welcome to the site %s", member.getFullname());

        String nonce = Integer.toString((int)(Math.random() * 1000));
        String hashDetail = Integer.toString(member.getMemberId()) + member.getUsername() + nonce;
        String detailContent = String.format("Id: %s, Name: %s, Nonce: %s, Hash: %s", member.getMemberId(), member.getUsername(), nonce, hashDetail.hashCode());




        return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Login</h2>" +
			"  <div>" + welcomeContent + "</div>" +
			"  <div>" + detailContent + "</div>" +
            "  <p><a href='/search?query='>To Search Page</a></p>" +
			"  <div><a href=\"/verify\">Verify Cookie</a></div>" +
			"</body>" +
			"</html>";
    }


	@CrossOrigin(origins = "*")
	@GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String verify(HttpServletResponse response, @CookieValue(value="Session") String sessionid) {


	  try{

		if (sessionid == null) {
			response.setStatus(401);
			return "Unauthorized";
		}

		int userid = SessionManager.verifycookie(sessionid);

	
		String content = String.format("Session Id = %s, UserId = %d", sessionid, userid); 

        return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Login</h2>" +
			"  <div>" + content + "</div>" +
            "  <p><a href='/search?query='>To Search Page</a></p>" +
			"</body>" +
			"</html>";
    
	  }
	  catch (Exception ex) {
		return "error";
	  }
	}

	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String search(HttpServletResponse response, @CookieValue(value="Session") String sessionid, @RequestParam String query) {

	  try{
 
		if (sessionid == null) {
			response.setStatus(401);
			return "Unauthorized";
		}

		int userid = SessionManager.verifycookie(sessionid);

	
		String content = String.format("showing search results for: %s ", query); 
		
        // PART 4 - OUTPUT ENCODING FIX FOR REFLECTED XSS
		// String encodedQuery = Encode.forHtml(query);
		
		// String content = String.format(
		// 	"showing search results for: %s ",
		// 	encodedQuery
		// );
		return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Search </h2>" +
			"  <h3>" + content + "</h3>" +

			"  <div> This page is a reflected XSS demonstration page.  Any value from the query parameter will be reflected to the page  See the following link for an example</div>" +
			"  <div><a href='/search?query=Hello%20Friend'>Friendly Link</a></div>" +
			"  <div><a href='/search?query=%3Cscript%3Ealert%28%27XSS%27%29%3B%3C%2Fscript%3E'>Malicious Link</a></div>" +
            "  <div><a href=\"/verify\">Verify Cookie</a></div>" +
			"</body>" +
			"</html>";
    
	  }
	  catch (Exception ex) {
		return "error";
	  }
	}

}