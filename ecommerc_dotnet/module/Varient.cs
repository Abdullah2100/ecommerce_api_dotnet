namespace ecommerc_dotnet.module;

public class Varient
{
    public Guid id { get; set; }
    public string name { get; set; }
    
    public ICollection<ProductVarient> productVarients { get; set; }
}