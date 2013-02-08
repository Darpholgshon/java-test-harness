public aspect LoggerAspect
{
	pointcut errorOrFatal(): execution(* error (..) ) || execution(* fatal (..) );
	
	void around(): errorOrFatal()
	{
		System.out.print("BAD: ");
		proceed();
	}
}
