package micdoodle8.mods.crossbowmod;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		CrossbowModClient.preInit(event);
	}
	
	@Override
	public void load(FMLInitializationEvent event)
	{
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		CrossbowModClient.init(event);
        NetworkRegistry.instance().registerChannel(new ClientPacketHandler(), "CrossbowMod", Side.CLIENT);
	}

	@Override
	public void registerRenderInformation()
	{
		CrossbowModClient.registerRenderInformation();
	}
	
	@Override
	public void onCrossbowHeld(ItemStack itemstack, World world, EntityPlayer player)
	{
		//Handled client-side
	}
	
	@Override
	public void onCrossbowClicked(ItemStack itemstack, World world, EntityPlayer player)
	{
		//Handled client-side
	}
	
	public class ClientTickHandler implements ITickHandler
	{
	    @Override
	    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	    @Override
	    public void tickEnd(EnumSet<TickType> type, Object... tickData)
	    {
	        if (type.equals(EnumSet.of(TickType.RENDER)))
	        {
	            onRenderTick();
	        }
	        else if (type.equals(EnumSet.of(TickType.CLIENT)))
	        {
	            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
	            if (guiscreen != null)
	            {
	                onTickInGUI(guiscreen);
	            } 
	            else 
	            {
	                onTickInGame();
	            }
	        }
	    }

	    @Override
	    public EnumSet<TickType> ticks()
	    {
	        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	    }

	    @Override
	    public String getLabel() { return null; }


	    public void onRenderTick()
	    {
	    	CrossbowModClient.onRenderTick();
	    }

	    public void onTickInGUI(GuiScreen guiscreen)
	    {
	    	CrossbowModClient.onTickInGUI(guiscreen);
	    }

	    public void onTickInGame()
	    {
	    	CrossbowModClient.onTickInGame();
	    }
	}
	
	public class ClientPacketHandler implements IPacketHandler
	{
		@Override
		public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) 
		{
			CrossbowModClient.onPacketData(manager, packet, player);
		}
	}
}
