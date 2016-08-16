package calculator;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/harris-benedict")
public class CalorieCalculator {
	
	private static final Logger logger = LogManager.getFormatterLogger("CalorieCalculatorService");

	@GET
	@Path("/bmr")
	@Produces(MediaType.TEXT_PLAIN)
	public Response calcHarrisBenedict(
			@DefaultValue("-") @QueryParam("sex") String sex,
			@DefaultValue("-1") @QueryParam("weight") double weight,
			@DefaultValue("-1") @QueryParam("height") double height,
			@DefaultValue("-1") @QueryParam("age") int age)
	{
		
		if (sex.equals("-") || weight < 0 || height < 0 || age < 0)
			return Response.status(Status.BAD_REQUEST).build();
		
		double ret = harrisBenedict(sex.charAt(0), weight, height, age);
				
		return Response.ok(ret).build();		
	}
	
	@GET
	@Path("/recommended_intake")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recommendedIntake(
			@DefaultValue("-") @QueryParam("sex") String sex,
			@DefaultValue("-1") @QueryParam("weight") double weight,
			@DefaultValue("-1") @QueryParam("height") double height,
			@DefaultValue("-1") @QueryParam("age") int age,
			@DefaultValue("-1") @QueryParam("activity") double activity)
	{
		if (sex.equals("-") || weight < 0 || height < 0 || age < 0 || activity < 0)
			return Response.status(Status.BAD_REQUEST).build();
		
		double ret = harrisBenedict(sex.charAt(0), weight, height, age);
		
		ret = ret * activity;
		logger.debug("PAL factor is %f => recommended intake = %f", activity,  ret);
		
		return Response.ok(ret).build();		
	}

	private double harrisBenedict(char sex, double weight, double height, int age) {
		double ret = 10.0* weight + 6.25*height - 5*age;
		if (sex == 'm')
			ret += 5.0;
		else if (sex == 'f')
			ret -= 161;
		
		if (sex == 'f')
			logger.info("BMR woman (%s): 10 * %.1fkg   +   6.25 * %.2fcm   -   5 * %dyears   -   161   =   %f", sex, weight, height, age, ret);
		else
			logger.info("BMR man (%s): 10 * %.1fkg + 6.25 * %.2fcm   -   5 * %dyears   +   5   =   %f", sex, weight, height, age, ret);
		
		return ret;
	}
}
