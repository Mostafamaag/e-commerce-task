package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.NotFoundException;
import flagak.com.e_commerce.model.AccountPayable;
import flagak.com.e_commerce.model.Order;
import flagak.com.e_commerce.model.OrderItem;
import flagak.com.e_commerce.model.Vendor;
import flagak.com.e_commerce.repository.AccountPayableRepository;
import flagak.com.e_commerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AccountPayableService {

    private final AccountPayableRepository accountPayableRepository;
    private final AccountReceivableService accountReceivableService;
    private final VendorRepository vendorRepository;

    public AccountPayableService(AccountPayableRepository accountPayableRepository, AccountReceivableService accountReceivableService, VendorRepository vendorRepository) {
        this.accountPayableRepository = accountPayableRepository;
        this.accountReceivableService = accountReceivableService;
        this.vendorRepository = vendorRepository;
    }

    // create account payable based on an order
    public void createAccountPayable(Order order) {

        Map<Vendor, Double> vendorAmounts = new HashMap<>();

        // calculate total amount payable to each vendor
        for (OrderItem item : order.getItems()) {
            Vendor vendor = item.getProduct().getVendor();
            Double amount = item.getQuantity() * item.getPrice();
            vendorAmounts.put(vendor, vendorAmounts.getOrDefault(vendor, 0.0) + amount);
        }

        // save each payable record
        for (Map.Entry<Vendor, Double> entry : vendorAmounts.entrySet()) {
            Vendor vendor = entry.getKey();
            Double amount = entry.getValue();

            AccountPayable payable = new AccountPayable();
            payable.setVendor(vendor);
            payable.setAmount(amount);
            payable.setOrder(order); // ??
            payable.setPaid(false);
            accountPayableRepository.save(payable);
        }
    }

    // pay for vendor
    public void pay(Long payableId) {
        AccountPayable payable = accountPayableRepository.findById(payableId)
                .orElseThrow(() -> new NotFoundException("Payable not found"));

        accountReceivableService.createAccountReceivable(payable);
        accountPayableRepository.delete(payable);

    }

    // fetch all payable
    public List<AccountPayable> getPayables(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(
                () -> new NotFoundException("Vendor not found"));

        List<AccountPayable> payables = accountPayableRepository.findByVendor(vendor);
        for(AccountPayable payable : payables) {
            System.out.println(payable.getVendor().getEmail());
        }

        return payables;
    }
}

