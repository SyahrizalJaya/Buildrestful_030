/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PWS.A.tutorialspoint;

// Import
import java.util.HashMap;
import java.util.Map;
import model2.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author ACER
 */
@RestController //memanggil Restful controller
public class Controller { //kelas public bernama Controller
    private static Map<String, Product> productRepo = new HashMap<>(); //menggunakan HashMap untuk menympan data dalam bentuk string kedalam Product.Java
static{
    Product honey = new Product(); //mendeklarasikan variabel baru dengan nama honey
    honey.setId("1"); //menentukan id ke-1
    honey.setName("Honey"); //menuliskan nama dari id ke-1 yaitu "Honey"
    honey.setPrice(15000.0);
    honey.setDiscount(15);
    honey.setTotal(honey.getPrice()-(honey.getPrice()*honey.getDiscount())/100);
    productRepo.put(honey.getId(), honey);
    
    Product almond = new Product(); //mendeklarasikan variabel baru dengan nama almond
    almond.setId("2"); //menentukan id ke-2
    almond.setName("Almond"); //menuliskan nama dari id ke-1 yaitu "Almond"
    almond.setPrice(10000.0);
    almond.setDiscount(10);
    almond.setTotal(almond.getPrice()-(almond.getPrice()*almond.getDiscount())/100);
    productRepo.put(almond.getId(), almond);
    }
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE) //path untuk melakukan delete data ketika id pada kode => "/products/{id}"
    public ResponseEntity<Object> delete(@PathVariable("id")String id){
        //jika tidak ada id untuk di hapus maka akan mucul pesan error "product could not be found"
        if(!productRepo.containsKey(id))
                {
                    return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
                }
        //jika ada id untuk dihapus maka muncul pesan "Product is deleted successfully"
        else    {
                    productRepo.remove(id); 
                    return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK); //Memunculkan pesan "Product is deleted successfully"
                }
    }
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT) //path untuk melakukan edit data ketika id pada kode => "/products/{id}"
    public ResponseEntity<Object> updateProduct(@PathVariable("id")String id, @RequestBody Product product){
        //jika tidak ada id untuk di edit maka akan muncul pesan error "product could not be found"
        if(!productRepo.containsKey(id))
                {
                    return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
                }
        //jika ada id yang yang dapat di edit maka mucul pesan "Product is update successsfully"
        else 
        {
            product.setTotal(product.getPrice()-(product.getPrice()*product.getDiscount())/100);
            productRepo.remove(id);
            product.setId(id);
            productRepo.put(id, product);
            return new ResponseEntity<>("Product is update successsfully", HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/products",method = RequestMethod.POST) //Untuk menambahkan data pada path "/products"
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //Jika id yang dimasukkan telah ada maka akan mucul pesan error "The product is already on the list"
        if(productRepo.containsKey(product.getId()))
                {
                    return new ResponseEntity<>("The product is already on the list", HttpStatus.CONFLICT);
                }
        //Jika id yang dimasukkan belum ada dalam list maka muncul pesan "Product added successfully"
        else 
        {
            product.setTotal(product.getPrice()-(product.getPrice()*product.getDiscount())/100);
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        }
    }
    
    //menampilkan produk pada path "/products"
    @RequestMapping(value="/products")
    public ResponseEntity<Object> getProduct(){
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);//
    }
    
    
}
