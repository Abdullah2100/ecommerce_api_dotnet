using ecommerc_dotnet.module;

namespace ecommerc_dotnet.dto.Response;

public class SubCategoryResponseDto
{
    public Guid id { get; set; }
    public string name { get; set; }
    public Guid category_id { get; set; } 
    public Guid store_id { get; set; } 
    
}