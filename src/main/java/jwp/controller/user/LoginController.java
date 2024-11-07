package jwp.controller.user;

import core.mvc.AbstractController;
import core.mvc.view.JspView;
import core.mvc.view.ModelAndView;
import jwp.dao.UserDao;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements AbstractController {

    private final UserDao userDao = new UserDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        User loginUser = new User(userId, password);
        User user = userDao.findByUserId(userId);

        if (user != null && user.isSameUser(loginUser)) {
            session.setAttribute("user", user);
            return jspView("redirect:/");
        }
        return jspView("redirect:/user/loginFailed");
    }

    @Override
    public ModelAndView jspView(String url) {
        return new ModelAndView(new JspView(url));
    }

    @Override
    public ModelAndView jsonView( ) {
        return null;
    }
}
