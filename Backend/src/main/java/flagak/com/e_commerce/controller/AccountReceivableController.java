package flagak.com.e_commerce.controller;


import flagak.com.e_commerce.model.AccountReceivable;
import flagak.com.e_commerce.service.AccountReceivableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receivables")
@PreAuthorize("hasRole('ROLE_VENDOR')")
public class AccountReceivableController {
    private final AccountReceivableService accountReceivableService;

    @Autowired
    public AccountReceivableController(AccountReceivableService accountReceivableService) {
        this.accountReceivableService = accountReceivableService;
    }


    @GetMapping()
    public List<AccountReceivable> getReceivables(HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        return accountReceivableService.getReceivables(vendorId);
    }

}
