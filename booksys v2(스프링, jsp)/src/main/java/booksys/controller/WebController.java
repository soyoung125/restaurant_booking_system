package booksys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	@RequestMapping(value="/")
	public String Home() {
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String Login() {
		return "login";
	}
	
	@RequestMapping(value="/loginAction")
	public String LoginAction() {
		return "loginAction";
	}
	
	@RequestMapping(value="/main")
	public String Main(ModelMap model) throws Exception {
		return "main";
	}
	
	@RequestMapping(value="/join")
	public String Join() {
		return "join";
	}
	
	@RequestMapping(value="/joinAction")
	public String JoinAction() {
		return "joinAction";
	}
	
	@RequestMapping(value="/logoutAction")
	public String LogoutAction() {
		return "logoutAction";
	}	
	
	@RequestMapping(value="/userManager")
	public String UserManager() {
		return "userManager";
	}
	
	@RequestMapping(value="/userDeleteAction")
	public String UserDeleteAction() {
		return "userDeleteAction";
	}
	
	@RequestMapping(value="/userBlockAction")
	public String UserBlockAction() {
		return "userBlockAction";
	}
	
	@RequestMapping(value="/userUnblockAction")
	public String UserUnblockAction() {
		return "userUnblockAction";
	}		
	
	@RequestMapping(value="/reservationArrivalAction")
	public String ReservationArrivalAction(HttpServletRequest request, Model model) {
		model.addAttribute("oid", request.getParameter("oid"));
		model.addAttribute("userId", request.getParameter("userId"));
		return "reservationArrivalAction";
	}	
	
	@RequestMapping(value="/tableManager")
	public String TableManager() {
		return "tableManager";
	}
	
	@RequestMapping(value="/tableDeleteAction")
	public String TableDeleteAction() {
		return "tableDeleteAction";
	}
	
	@RequestMapping(value="/tableUpdateAction")
	public String TableUpdateAction() {
		return "tableUpdateAction";
	}
	
	@RequestMapping(value="/tableAddAction")
	public String TableAddAction() {
		return "tableAddAction";
	}
	
	@RequestMapping(value="/userUpdate")
	public String UserUpdate() {
		return "userUpdate";
	}
	
	@RequestMapping(value="/userUpdateAction")
	public String UserUpdateAction() {
		return "userUpdateAction";
	}
	
	@RequestMapping(value="/userPasswordUpdate")
	public String UserPasswordUpdate() {
		return "userPasswordUpdate";
	}
	
	@RequestMapping(value="/userPasswordUpdateAction")
	public String UserPasswordUpdateAction() {
		return "userPasswordUpdateAction";
	}
	
	@RequestMapping(value="/mypage")
	public String Mypage() {
		return "myPage";
	}
	
	@RequestMapping(value="/calendar")
	public String Calendar() {
		return "calendar";
	}
	
	@RequestMapping(value="/selectTime")
	public String Time(HttpServletRequest request, Model model) {
		model.addAttribute("date", request.getParameter("date"));
		return "selectTime";
	}
	
	@RequestMapping(value="/reservationCovers")
	public String ReservationCovers(HttpServletRequest request, Model model) {
		model.addAttribute("table", request.getParameter("table"));
		model.addAttribute("date", request.getParameter("date"));
		model.addAttribute("time", request.getParameter("time"));
		return "reservationCovers";
	}
	
	@RequestMapping(value="/reservationAddAction")
	public String ReservationAction(HttpServletRequest request, Model model) {
		model.addAttribute("covers", request.getParameter("covers"));
		model.addAttribute("table", request.getParameter("table"));
		model.addAttribute("date", request.getParameter("date"));
		model.addAttribute("time", request.getParameter("time"));
		return "reservationAddAction";
	}
	
	@RequestMapping(value="/reservationCancleAction")
	public String reservationCancleAction() {
		return "reservationCancleAction";
	}
	
	@RequestMapping(value="/walkinCovers")
	public String WalkinCovers(HttpServletRequest request, Model model) {
		model.addAttribute("table", request.getParameter("table"));
		model.addAttribute("date", request.getParameter("date"));
		model.addAttribute("time", request.getParameter("time"));
		return "walkinCovers";
	}
	
	@RequestMapping(value="/walkinAddAction")
	public String WalkinAction(HttpServletRequest request, Model model) {
		model.addAttribute("covers", request.getParameter("covers"));
		model.addAttribute("table", request.getParameter("table"));
		model.addAttribute("date", request.getParameter("date"));
		model.addAttribute("time", request.getParameter("time"));
		return "walkinAddAction";
	}
	
	@RequestMapping(value="/walkinDeleteAction")
	public String WalkinDeleteAction() {
		return "walkinDeleteAction";
	}
	
	@RequestMapping(value="/walkinDeleteAction2")
	public String WalkinDeleteAction2(HttpServletRequest request, Model model) {
		model.addAttribute("oid", request.getParameter("oid"));
		return "walkinDeleteAction2";
	}
}
