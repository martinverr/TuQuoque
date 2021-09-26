package com.tuquoque.game.ui.inventory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.ui.Item;
import com.tuquoque.game.world.Player;


public class Inventory extends Table {
    private final Array<InventorySlot> inventory;
    private Player player;
    private final int ROWS = 3;
    private final int COLUMNS = 5;
    private final int NO_BOTTOM_SLOTS = 4;
    private final int HELMET_SLOT_INDEX = ROWS*COLUMNS;
    private final int CHEST_SLOT_INDEX = ROWS*COLUMNS+1;
    private final int BOOTS_SLOT_INDEX = ROWS*COLUMNS+2;
    private final int WEAPON_SLOT_INDEX = ROWS*COLUMNS+3;

    private final DragAndDrop dragAndDrop;

    public Inventory(Skin skin, Player player, GameStarter context){
        super(skin);
        setVisible(false);
        dragAndDrop = new DragAndDrop();
        inventory = new Array<>(COLUMNS*ROWS+NO_BOTTOM_SLOTS);

        final Stack title;
        final Table inventorySlotsTable;
        final Table inventoryBottomSlotsTable;

        // Title Setup
        title = new Stack();
        title.add(new Image(skin.getDrawable("inventoryTop")));
        Label titleLabel = new Label("INVENTARIO", skin, "small-white");
        titleLabel.setAlignment(1);
        titleLabel.setColor(1,1,1,1);
        titleLabel.setFontScale(1.5f);
        title.add(titleLabel);

        // Main inventory slots setup
        inventorySlotsTable = new Table(skin);
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLUMNS; c++){
                String name = "invSlot";
                if(r == 0)
                    name+="-top";
                else if(r == ROWS-1)
                    name+="-bottom";
                else
                    name+="-mid";

                if(c == 0)
                    name+="-left";
                else if(c == COLUMNS-1)
                    name+="-right";
                else
                    name+="-mid";


                //add(new Image(skin.getDrawable(typeOfSlot)));
                InventorySlot inventorySlot = new InventorySlot(name, skin);

                inventorySlotsTable.add(inventorySlot);
                inventory.add(inventorySlot);
                dragAndDrop.addSource(new SlotSource(inventorySlot));
                dragAndDrop.addTarget(new SlotTarget(inventorySlot));

            }
            inventorySlotsTable.row();
        }

        // Accessories slots setup
        inventoryBottomSlotsTable = new Table(skin);
        String[] namesBottomSlots = {"invSlot-helmet", "invSlot-chest", "invSlot-boots", "invSlot-weapon"};

        for(String bottomSlotName : namesBottomSlots){
            InventorySlot inventorySlot = new InventorySlot(bottomSlotName, skin, bottomSlotName.split("-")[1]);
            inventoryBottomSlotsTable.add(inventorySlot);
            inventory.add(inventorySlot);
            dragAndDrop.addSource(new SlotSource(inventorySlot));
            dragAndDrop.addTarget(new SlotTarget(inventorySlot));
        }

        // Complete inventory setup
        add(title);
        row();
        add(inventorySlotsTable);
        row();
        add(inventoryBottomSlotsTable);

        addItemToInventoryAtIndex(new Item("helmet_01", 200, 1, "helmet"), HELMET_SLOT_INDEX);
    }

    public void open(){
        setVisible(true);
    }

    public void close(){
        setVisible(false);
    }

    public boolean isOpened(){
        return isVisible();
    }

    public void printInventory(){
        int index = 0;
        System.out.println("Inventory\n{");
        for(InventorySlot slot : inventory){
            System.out.print("\t" + index + ": ");
            if(slot.containsItems())
                System.out.println("["+slot.getItem()+"]; ");
            else
                System.out.println("[];");

            index++;
        }
        System.out.println("}\n");
    }

    private int indexOf(Item item){
        int index = 0;

        for(InventorySlot slot : inventory){
            if(slot.containsItems() && slot.getItem().equals(item))
                return index;
            index++;
        }
        return -1;
    }

    public int addItemToInventory(Item item){
        if(indexOf(item) != -1){
            InventorySlot slot = inventory.get(indexOf(item));
            slot.addItem(item);
            return slot.getItem().getQuantity();
        }

        for(InventorySlot slot : inventory){
            if(!slot.containsItems()){
                slot.addItem(item);
                return item.getQuantity();
            }
        }
        return 0;
    }

    public int addItemToInventoryAtIndex(Item item, int index){
        inventory.get(index).addItem(item);
        return item.getQuantity();
    }

    class SlotSource extends DragAndDrop.Source{
        public SlotSource (InventorySlot inventorySlot){
            super(inventorySlot);
        }


        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            InventorySlot slotSource = (InventorySlot) getActor();

            if(slotSource.containsItems()){
                payload.setObject(slotSource);
                payload.setDragActor(slotSource.getItem().getItemImage(getSkin()));
                return payload;
            }
            else
                return null;
        }
    }

    class SlotTarget extends DragAndDrop.Target{
        public SlotTarget (InventorySlot inventorySlot){
            super(inventorySlot);
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            InventorySlot draggedSlot = (InventorySlot) payload.getObject();
            InventorySlot targetSlot = (InventorySlot) getActor();

            //false if target is the source or the item(type) is not acceptable
            return !draggedSlot.equals(targetSlot) && targetSlot.isItemAccepted(draggedSlot.getItem());
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            InventorySlot sourceSlot = (InventorySlot) source.getActor();
            InventorySlot targetSlot = (InventorySlot) getActor();
            if (!targetSlot.containsItems() || targetSlot.getItem().equals(sourceSlot.getItem())){
                targetSlot.addItem(sourceSlot.getItem());
                sourceSlot.removeItem();
            }
            else{
                Item tempSwap = new Item(sourceSlot.getItem().getName(), sourceSlot.getItem().getID(), sourceSlot.getItem().getQuantity());
                sourceSlot.addItem(targetSlot.getItem());
                targetSlot.addItem(tempSwap);
            }

        }
    }
}