package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                        Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);
        //model.addAttribute("item",item);
        //자동추가해주기 때문에 생략가능
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        //model.addAttribute("item",item);
        //자동추가해주기 때문에 생략가능
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);
        //model.addAttribute("item",item);
        //자동추가해주기 때문에 생략가능
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);
        //model.addAttribute("item",item);
        //자동추가해주기 때문에 생략가능
        return "redirect:/basic/items/"+item.getId();
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId ,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";

//        return "basic/item";
        //return은 해당 파일(view)를 불러와서 model이라던지 파라미터를 넣어주는 개념임
        //redirect는 해당 링크로 요청을 서버로 보내는거임
        //따라서  return "/basic/items/"+{itemId};는 말도안되는거임
        // basic/items/3이라는 "파일"을 가져오라는거지 그 경로의 결과를 주라는게 아니기때문임
        // 또한 redirect를 하지않고 그냥 return으로 한후 새로고침 누르면 방금 했던
        // view에 파라미터 넣은거 주는 return이 반복되어 똑같은 명령어 여러번하게되는거임
        //하지만 redirect로 한다면 본문 메시지가 삭제되고 메소드가 get으로 변경하고 새로운 요청을 보냄 새로고침으로 반복적으로해도 새로고침은 마지막 명령어를 다시 실행하는것이기때문에 redirect로 만들어진 get이 실행되지 post명령어가 실행되지않음
        // 다시말해 A명령어(post.302) 후 redirect명령(get) 이라면 A명령을 날려버리고 redirect만 계속한다는 말
        // 크롬으로 보면 이렇게됨 처음 A명령어(post,302), redirect명렁어(get.302)
    }
    //테스트용 데이터 추가, 의존성 주입이 끝난 후 , 실행되는 어노태이션, 주로 초기화할 메소드가 있을때 쓴다고함
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 20000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

    }

}
