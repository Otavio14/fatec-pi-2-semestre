export const Select = ({ Label, FitContent, ...rest }) => {
  return (
    <div className={`flex flex-col ${FitContent ? "w-fit" : "w-full"}`}>
      <label className="mb-[3px] text-[18px] font-medium leading-[26px] text-[#0c2d57]">
        {Label}
      </label>
      <select
        {...rest}
        style={{ transition: "border-color .5s" }}
        className={`h-[50px] w-full rounded border-none bg-[#e7eaee] px-3 py-2 align-middle text-[14px] leading-[18px] text-[#5c728e] ${FitContent ? "mb-0" : "mb-[18px]"}`}
      ></select>
    </div>
  );
};
