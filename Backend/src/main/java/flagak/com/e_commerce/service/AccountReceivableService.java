package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.NotFoundException;
import flagak.com.e_commerce.model.AccountPayable;
import flagak.com.e_commerce.model.AccountReceivable;
import flagak.com.e_commerce.model.Vendor;
import flagak.com.e_commerce.repository.AccountReceivableRepository;
import flagak.com.e_commerce.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountReceivableService {
    private final VendorRepository vendorRepository;
    private final AccountReceivableRepository accountReceivableRepository;

    @Autowired
    public AccountReceivableService(VendorRepository vendorRepository, AccountReceivableRepository accountReceivableRepository) {
        this.vendorRepository = vendorRepository;
        this.accountReceivableRepository = accountReceivableRepository;
    }

    public List<AccountReceivable> getReceivables(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

        return accountReceivableRepository.findByVendor(vendor);
    }


    public void createAccountReceivable(AccountPayable accountPayable) {
        AccountReceivable receivable = new AccountReceivable();
        receivable.setVendor(accountPayable.getVendor());
        receivable.setAmount(accountPayable.getAmount());
        receivable.setOrder(accountPayable.getOrder());
        receivable.setPaid(true);
        accountReceivableRepository.save(receivable);

    }
}
