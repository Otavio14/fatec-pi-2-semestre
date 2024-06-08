export const Input = ({
  Label,
  Textarea = false,
  Rows = 15,
  Cols = 50,
  ...rest
}) => {
  return (
    <div className="flex w-full flex-col">
      <label className="mb-[3px] text-[18px] font-medium leading-[26px] text-[#0c2d57]">
        {Label}
      </label>
      {Textarea ? (
        <textarea
          {...rest}
          rows={Rows}
          cols={Cols}
          style={{ transition: "border-color .5s" }}
          className="mb-[18px] min-h-[50px] w-full rounded border-none bg-[#e7eaee] px-3 py-2 align-middle text-[14px] leading-[18px] text-[#5c728e]"
        ></textarea>
      ) : (
        <input
          {...rest}
          style={{ transition: "border-color .5s" }}
          className="mb-[18px] h-[50px] w-full rounded border-none bg-[#e7eaee] px-3 py-2 align-middle text-[14px] leading-[18px] text-[#5c728e]"
        />
      )}
    </div>
  );
};
