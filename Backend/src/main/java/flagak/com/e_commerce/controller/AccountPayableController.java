package flagak.com.e_commerce.controller;


import flagak.com.e_commerce.model.AccountPayable;
import flagak.com.e_commerce.service.AccountPayableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payables")
@PreAuthorize("hasRole('ROLE_VENDOR')")
public class AccountPayableController {
    private final AccountPayableService accountPayableService;

    public AccountPayableController(AccountPayableService accountPayableService) {
        this.accountPayableService = accountPayableService;
    }


    @GetMapping()
    public List<AccountPayable> getPayables(HttpServletRequest request) {
        Long vendorId = (Long) request.getAttribute("id");
        return accountPayableService.getPayables(vendorId);
    }

    // this route should be accessed only for the admin or who's can make this transaction
    @PostMapping ("/pay/{payableId}")
    public void pay(@PathVariable Long payableId) {
        accountPayableService.pay(payableId);
    }

}
