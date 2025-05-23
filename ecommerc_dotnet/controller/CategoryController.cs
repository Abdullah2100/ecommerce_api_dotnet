using ecommerc_dotnet.context;
using ecommerc_dotnet.data;
using ecommerc_dotnet.dto.Response;
using ecommerc_dotnet.midleware.ConfigImplment;
using hotel_api.Services;
using hotel_api.util;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace ecommerc_dotnet.controller;

[Authorize]
[ApiController]
[Route("api/Category")]
public class CategoryController : ControllerBase
{
    public CategoryController(AppDbContext dbContext
        , IConfig configuration,
        IWebHostEnvironment webHostEnvironment
    )
    {
        _host = webHostEnvironment;
        _categoryData = new CategoryData(dbContext);
        _userData = new UserData(dbContext, configuration);
        _configuration = configuration;
    }

    private readonly CategoryData _categoryData;
    private readonly UserData _userData;
    private readonly IConfig _configuration;
    private readonly IWebHostEnvironment _host;


    [HttpGet("all/{pageNumber:int}")]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<IActionResult>getCatgory(int pageNumber = 1)
    {
        if (pageNumber < 1)
            return BadRequest("خطء في البيانات المرسلة");

        var categories = await _categoryData.getCategories(_configuration, pageNumber);
        if (categories == null)
            return BadRequest("لا يوجد اي اقسام");
        return Ok(categories);
    }


    [HttpPost("")]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    public async Task<IActionResult> createCateogry([FromForm] CategoryRequestDto category)
    {
        var authorizationHeader = HttpContext.Request.Headers["Authorization"];
        var id = AuthinticationServices.GetPayloadFromToken("id",
            authorizationHeader.ToString().Replace("Bearer ", ""));
        Guid? idHolder = null;
        if (Guid.TryParse(id?.Value.ToString(), out Guid outID))
        {
            idHolder = outID;
        }

        if (idHolder == null)
        {
            return Unauthorized("هناك مشكلة في التحقق");
        }

        var user = await _userData.getUserById(idHolder.Value);
        if (user.role == 1)
            return BadRequest("ليس لديك الصلاحية لانشاء قسم جديد");

        bool isExist = await _categoryData.isExist(category.name);

        if (isExist)
            return BadRequest("هناك قسم بهذا الاسم");

        var imagePath = await clsUtil.saveFile(category.image, clsUtil.enImageType.CATEGORY, _host);
        // var imagePath = await MinIoServices.uploadFile(_configuration,category.image,MinIoServices.enBucketName.CATEGORY); ;

        if (imagePath == null)
            return BadRequest("حدثة مشكلة اثناء حفظ الصورة");

        var result = await _categoryData.addNewCategory(category.name, imagePath, user.ID);

        if (result == null)
            return BadRequest("حدثت مشكلة اثناء حفظ القسم");

        return StatusCode(201, "تم انشاء القسم بنجاح");
    }

    [HttpPut("")]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<IActionResult> updateCateogry([FromForm] CategoryRequestUpdatteDto category)
    {
        var authorizationHeader = HttpContext.Request.Headers["Authorization"];
        var id = AuthinticationServices.GetPayloadFromToken("id",
            authorizationHeader.ToString().Replace("Bearer ", ""));
        Guid? idHolder = null;
        if (Guid.TryParse(id?.Value.ToString(), out Guid outID))
        {
            idHolder = outID;
        }

        if (idHolder == null)
        {
            return Unauthorized("هناك مشكلة في التحقق");
        }

        var user = await _userData.getUserById(idHolder.Value);

        if (user == null)
        {
            return BadRequest("المستخدم غير موجود");
        }
        if (user.role == 1)
            return BadRequest("ليس لديك الصلاحية لانشاء قسم جديد");

        var categoryHolder = await _categoryData.getCategory(category.id);

        if (categoryHolder == null)
            return BadRequest("القسم غير موجود");


        bool isExistName = false;

        if (category?.name != null)
            isExistName = await _categoryData.isExist(category.name);

        if (isExistName&&categoryHolder.id!=category.id)
            return BadRequest("هناك قسم بهذا الاسم");

        string? imagePath = null;

        if (category?.image != null)
        {
            if(categoryHolder.image_path!=null)
                        clsUtil.deleteFile(categoryHolder.image_path, _host);
            imagePath = await clsUtil.saveFile(category.image, clsUtil.enImageType.CATEGORY, _host);
           
        }


        var result = await _categoryData.updateCategory(
            category!.id,
            category.name, 
            imagePath);

        if (result == null)
            return BadRequest("حدثت مشكلة اثناء حفظ القسم");

        return StatusCode(200, "تم تعديل  القسم بنجاح");
    }
    
    
    [HttpPut("status/{categoryId:guid}")]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<IActionResult> blockOrUnBlockCateogry( Guid categoryId)
    {
        var authorizationHeader = HttpContext.Request.Headers["Authorization"];
        var id = AuthinticationServices.GetPayloadFromToken("id",
            authorizationHeader.ToString().Replace("Bearer ", ""));
        Guid? idHolder = null;
        if (Guid.TryParse(id?.Value.ToString(), out Guid outID))
        {
            idHolder = outID;
        }

        if (idHolder == null)
        {
            return Unauthorized("هناك مشكلة في التحقق");
        }

        var user = await _userData.getUserById(idHolder.Value);
       
        if (user.role == 1)
            return BadRequest("ليس لديك الصلاحية لانشاء قسم جديد");

        var categoryHolder = await _categoryData.getCategory(categoryId);

        if (categoryHolder == null)
            return BadRequest("القسم غير موجود");

        var result = await _categoryData.blockOrUnBlockCategory(
            categoryId);

        if (result == null)
            return BadRequest("حدثت مشكلة اثناء حفظ القسم");

        return StatusCode(201, "تم تعديل  القسم بنجاح");
    }

}