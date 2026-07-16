package com.marketintelligence.modules.market.importer;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.marketintelligence.modules.market.entity.SuperstoreRecord;
import com.marketintelligence.modules.market.repository.SuperstoreRepository;
import com.opencsv.CSVReader;

@Component
public class SuperstoreImporter implements CommandLineRunner {

    private final SuperstoreRepository repository;
    private final Flyway flyway;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public SuperstoreImporter(SuperstoreRepository repository, Flyway flyway) {
        this.repository = repository;
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) throws Exception {

        // Ensure the table exists before querying/importing reference data.
        flyway.migrate();

        if(repository.count() > 0){

            System.out.println("----------------------------------------");
            System.out.println("Superstore dataset already imported.");
            System.out.println("----------------------------------------");
            return;
        }

        System.out.println("----------------------------------------");
        System.out.println("Importing Global Superstore Dataset...");
        System.out.println("----------------------------------------");
                ClassPathResource resource =
                new ClassPathResource("data/superstore.csv");

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {

csvReader.readNext(); // Skip header

String[] row;
List<SuperstoreRecord> records = new ArrayList<>();

int count = 0;

while ((row = csvReader.readNext()) != null) {

            try {

                SuperstoreRecord record = new SuperstoreRecord();

                // Row ID
                if (!row[0].isBlank()) {
                    // ignored because id is auto-generated
                }

                record.setOrderId(row[1]);

                record.setOrderDate(
                        LocalDate.parse(row[2], formatter));

                record.setShipDate(
                        LocalDate.parse(row[3], formatter));

                record.setShipMode(row[4]);

                record.setCustomerId(row[5]);

                record.setCustomerName(row[6]);

                record.setCustomerSegment(row[7]);

                record.setCity(row[8]);

                record.setState(row[9]);

                record.setCountry(row[10]);

                record.setPostalCode(row[11]);

                record.setMarket(row[12]);

                record.setRegion(row[13]);

                record.setProductId(row[14]);

                record.setCategory(row[15]);

                record.setSubCategory(row[16]);

                record.setProductName(row[17]);
                record.setSales(
                        new BigDecimal(row[18]));

                record.setQuantity(
                        Integer.parseInt(row[19]));

                record.setDiscount(
                        new BigDecimal(row[20]));

                record.setProfit(
                        new BigDecimal(row[21]));

                record.setShippingCost(
                        new BigDecimal(row[22]));

                record.setOrderPriority(row[23]);
                if (count < 5) {
    System.out.println("================================");
    System.out.println("Order ID    : " + record.getOrderId());
    System.out.println("Product     : " + record.getProductName());
    System.out.println("Customer    : " + record.getCustomerName());
    System.out.println("City        : " + record.getCity());
    System.out.println("Country     : " + record.getCountry());
    System.out.println("Category    : " + record.getCategory());
    System.out.println("SubCategory : " + record.getSubCategory());
}
                records.add(record);
                

                count++;

                // Save every 1000 rows
                if(records.size()==1000){

                    repository.saveAll(records);

                    records.clear();

                    System.out.println(
                            "Imported : " + count + " records");
                }

            }
            catch(Exception ex){

                System.out.println(
                        "Skipped row : " + count);

                ex.printStackTrace();

            }

        }

        if(!records.isEmpty()){

            repository.saveAll(records);

        }

        }

        System.out.println("----------------------------------------");
        System.out.println("Import Finished Successfully");
        System.out.println("Total Records Imported : " + repository.count());
        System.out.println("----------------------------------------");

    }

}
