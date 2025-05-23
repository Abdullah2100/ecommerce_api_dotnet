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
[Route("api/SubCategory")]
public class SubCategoryController : ControllerBase
{
    public SubCategoryController
    (
        AppDbContext dbContext,
        IConfig config
    )
    {
        _categoryData = new CategoryData(dbContext);
        _subCategoryData = new SubCategoryData(dbContext);
        _userData = new UserData(dbContext, config);
    }

    private readonly CategoryData _categoryData;
    private readonly SubCategoryData _subCategoryData;
    private readonly UserData _userData;

    [HttpPost("new")]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    public async Task<IActionResult> creatSubCategory([FromBody] SubCategoryRquestDto subCategory)
    {
        var authorizationHeader = HttpContext.Request.Headers["Authorization"];
        var id = AuthinticationServices.GetPayloadFromToken("id",
            authorizationHeader.ToString().Replace("Bearer ", ""));
        Guid? idHolder = null;
        if (Guid.TryParse(id.Value.ToString(), out Guid outID))
        {
            idHolder = outID;
        }

        if (idHolder == null)
        {
            return Unauthorized("هناك مشكلة في التحقق");
        }

        var existByName = !await _categoryData.isExist(subCategory.cateogy_id);
        if (existByName == true)
        {
            return BadRequest("القسم الذي ادخلته غير مودود");
        }

        var user = await _userData.getUser(idHolder.Value);

        if (user == null)
        {
            return BadRequest("المستخدم غير موجود");
        }

        var isBlockUser = await _userData.isExist(idHolder.Value);
        if (!isBlockUser)
        {
            return BadRequest("لا يمكنك انشاء فئة جديد يمكنك مراجعة مدير النظام لحل المشكلة");
        }

        var storeData = user.store_id;

        if (storeData == null)
        {
            return BadRequest("ليس لديك اي متجر يرجى فتح متجر لكي تكون قادرا على اضافة فئة");
        }

        // if(storeData.subcategory!=null && storeData.subcategory.Count > 20)
        // return BadRequest("لا يمكنك اضافة اكثر من 20 فئة في متجرك");


        var result = await _subCategoryData
            .createSubCategory(cateogy_id: subCategory.cateogy_id,
                store_id: (Guid)user.store_id!,
                name: subCategory.name);

        if (result == null)
            return BadRequest("حدثة مشكلة اثناء الفئة");
        return StatusCode(201, result);
    }

    [HttpPut("")]
    [ProducesResponseType(StatusCodes.Status401Unauthorized)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    public async Task<IActionResult> updateSubCategory([FromBody] SubCategoryRquestUpdateDto subCategory)
    {
        var authorizationHeader = HttpContext.Request.Headers["Authorization"];
        var id = AuthinticationServices.GetPayloadFromToken("id",
            authorizationHeader.ToString().Replace("Bearer ", ""));
        Guid? idHolder = null;
        if (Guid.TryParse(id.Value.ToString(), out Guid outID))
        {
            idHolder = outID;
        }

        if (idHolder == null)
        {
            return Unauthorized("هناك مشكلة في التحقق");
        }

        var existByName = !await _categoryData.isExist(subCategory.cateogy_id);
        if (existByName == true)
        {
            return BadRequest("القسم الذي ادخلته غير مودود");
        }

        var user = await _userData.getUser(idHolder.Value);

        if (user == null)
        {
            return BadRequest("المستخدم غير موجود");
        }

        var isBlockUser = await _userData.isExist(idHolder.Value);
        if (!isBlockUser)
        {
            return BadRequest("لا يمكنك انشاء فئة جديد يمكنك مراجعة مدير النظام لحل المشكلة");
        }

        var storeData = user.store_id;

        if (storeData == null)
        {
            return BadRequest("ليس لديك اي متجر يرجى فتح متجر لكي تكون قادرا على اضافة فئة");
        }

        if (await _subCategoryData.isExist((Guid)user.store_id!, (Guid)subCategory.id!) == null)
            return BadRequest("الفئة التي ادخلتها غير موجودة");


        var result = await _subCategoryData
            .updateSubCategory(id: subCategory.id,
                name: subCategory.name,
                category_id: subCategory.cateogy_id);

        if (result == null)
            return BadRequest("حدثة مشكلة اثناء الفئة");
        return StatusCode(201, result);
    }

    [HttpGet("{store_id}/{page:int}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public async Task<IActionResult> getSubCategory(Guid store_id, int page)
    {
        var result = await _subCategoryData
            .getSubCategory(store_id, page);

        return StatusCode(200, result);
    }
}