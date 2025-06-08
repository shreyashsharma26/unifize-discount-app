# unifize-discount-app

### Prerequisites
- Java version used: 21
- Maven version used: 3.9.10

#### Build and run instructions on terminal
- mvn clean install : to build the project.
- mvn test : to run test cases
- mvn javadoc:javadoc : to create javadocs

### Assumptions and Technical Decisions

#### Architecture Pattern

- Implemented using **Strategy Pattern** for each discount type:
    - `DiscountStrategy` interface
    - Separate strategy class for each discount type
    - Makes it easy to add new discount types later without modifying `DiscountServiceImpl`.
 
### Specific Assumptions for Each Discount Type

#### Brand Discounts
- Brand discounts are **global** and not tied to customer tier.
- Discounts are configured in an in-memory `Map<String, BigDecimal>` in `BrandDiscountStrategy`.

#### Category Discounts
- Category discounts are configured similarly to brand discounts — also global and available to all customers.

#### Voucher Discounts
- Vouchers are explicitly entered by the customer.
- Vouchers are configured in an in-memory `Map<String, VoucherData>` which contains:
    - Discount percentage
    - Expiration date
- Expired vouchers are not applied.
- No brand/category exclusions are implemented yet (as per "happy path only" instruction).
- Only `isValidVoucher(code)` is used to validate a voucher in the service.

#### Bank Offers
- Bank offer is applied only if:
    - `PaymentInfo` is present
    - `bankName` matches an eligible bank in the map.
 
### General Assumptions
- The discount application **order is fixed**:
    1. Brand Discount
    2. Category Discount
    3. Voucher Discount
    4. Bank Offer
 
- **Voucher discounts are explicitly provided by the user** at checkout via `voucherCode`.

- Each **CartItem** may have multiple discounts applied in sequence.

- All discounts are calculated on **total cart value**, not per product (except brand and category discounts which are product-based).
